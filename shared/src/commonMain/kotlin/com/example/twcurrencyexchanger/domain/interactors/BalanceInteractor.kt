package com.example.twcurrencyexchanger.domain.interactors

import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.mapper.toBalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.utils.Constants.AMOUNT_BY_DEFAULT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

interface BalanceInteractor {
    val balancesFlow: Flow<List<BalanceItemModel>>
}

class BalanceInteractorImpl(
    private val currencyRepository: CurrencyRepository,
    private val localDataSource: BalanceLocalDataSource
) : BalanceInteractor {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _balancesFlow =
        localDataSource.getBalances()
            .onEach { if (it.isEmpty()) init() }
            .filter { it.isNotEmpty() }
            .mapLatest { it.map { item -> item.toBalanceItemModel() } }

    private suspend fun initLocalDataSource(item: ExchangeRatesItem) {
        item.rates.map {
            localDataSource.insertBalance(
                BalanceItem(
                    type = it.key,
                    amount = if (it.key == item.base) AMOUNT_BY_DEFAULT else 0.0,
                    isBaseType = it.key == item.base
                )
            )
        }
    }

    suspend fun init() {
        val tmp = currencyRepository.getCurrencyExchangeRates()
        initLocalDataSource(tmp)
    }

    override val balancesFlow: Flow<List<BalanceItemModel>> = _balancesFlow


}