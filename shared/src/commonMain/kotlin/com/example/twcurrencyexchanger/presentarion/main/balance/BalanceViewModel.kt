package com.example.twcurrencyexchanger.presentarion.main.balance

import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState
import com.example.twcurrencyexchanger.utils.ExchangeRatesUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BalanceViewModel : BaseSharedViewModel<BalanceViewState, BalanceAction, BalanceEvent>(
    initialState = BalanceViewState()
) {

    private val balanceInteractor: BalanceInteractor = Inject.instance()
    private val currencyRepository: CurrencyRepository = Inject.instance()
    private val localDataSource: BalanceLocalDataSource = Inject.instance()
    private val updater = ExchangeRatesUpdater(currencyRepository, localDataSource)
    override fun obtainEvent(viewEvent: BalanceEvent) {
        when (viewEvent) {
            BalanceEvent.SettingClick -> {}
        }
    }

    init {
        startUpdater()
        observeBalances()
    }

    private fun startUpdater() {
        updater.start()
    }

    private fun observeBalances() {
        balanceInteractor.balancesFlow
            .filter { it.isNotEmpty() }
            .onEach { obtainBalances(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun obtainBalances(balances: List<BalanceItemModel>) {
        viewState = viewState.copy(items = balances)
    }
}