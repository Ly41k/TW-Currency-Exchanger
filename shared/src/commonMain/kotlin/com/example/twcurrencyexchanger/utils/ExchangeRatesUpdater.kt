package com.example.twcurrencyexchanger.utils

import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.utils.Constants.CURRENCY_UPDATE_DELAY_BY_DEFAULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ExchangeRatesUpdater(
    private val currencyRepository: CurrencyRepository,
    private val balanceLocalDataSource: BalanceLocalDataSource
) {

    private val exchangeRatesScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var job: Job? = null

    private val _exchangeRatesUpdateStateFlow = MutableStateFlow(false)
    private val _initializationDatabaseStateFlow = MutableStateFlow(false)
    private val _initializationStateFlow = MutableStateFlow(false)

    private val _enableUpdateEvent =
        MutableSharedFlow<Unit>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)

    private val _errorEvent =
        MutableSharedFlow<Throwable>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)

    fun start() {
        stop()
        job = exchangeRatesScope.launch(Dispatchers.Default) {
            observeExchangeRatesUpdateFlow(this)
            observeExchangeRatesUpdaterFlow(this)
        }
    }

    private fun stop() {
        job?.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeExchangeRatesUpdaterFlow(scope: CoroutineScope) =
        _exchangeRatesUpdateStateFlow
            .onStart { _enableUpdateEvent.tryEmit(Unit) }
            .filter { it }
            .flatMapLatest {
                flow { emit(currencyRepository.getCurrencyExchangeRates()) }
                    .combine(_initializationDatabaseStateFlow) { a, b ->
                        a to b
                    }.flatMapLatest { (item, isDatabaseInited) ->
                        if (!isDatabaseInited) {
                            balanceLocalDataSource.getBalances()
                                .onEach { if (it.isEmpty()) balanceLocalDataSource.initLocalDataSource(item) }
                                .map {
                                    _initializationDatabaseStateFlow.tryEmit(true)
                                    Unit
                                }
                        } else {
                            flow { emit(Unit) }
                        }
                    }
                    .catch { throwable ->
                        _enableUpdateEvent.tryEmit(Unit)
                        _exchangeRatesUpdateStateFlow.tryEmit(false)
                        _errorEvent.tryEmit(throwable)
                    }
            }
            .onEach {
                _enableUpdateEvent.tryEmit(Unit)
                _exchangeRatesUpdateStateFlow.tryEmit(false)
            }
            .catch { throwable -> _errorEvent.tryEmit(throwable) }
            .flowOn(Dispatchers.IO)
            .launchIn(scope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeExchangeRatesUpdateFlow(scope: CoroutineScope) = _enableUpdateEvent
        .mapLatest {
            val initialization = _initializationStateFlow.value
            if (initialization) {
                delay(CURRENCY_UPDATE_DELAY_BY_DEFAULT)
            } else {
                _initializationStateFlow.tryEmit(true)
            }
            _exchangeRatesUpdateStateFlow.tryEmit(true)
            Unit
        }
        .catch { throwable -> _errorEvent.tryEmit(throwable) }
        .flowOn(Dispatchers.Default)
        .launchIn(scope)
}