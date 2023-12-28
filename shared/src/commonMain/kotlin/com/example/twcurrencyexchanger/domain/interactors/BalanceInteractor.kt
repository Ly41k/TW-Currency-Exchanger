package com.example.twcurrencyexchanger.domain.interactors

import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import com.example.twcurrencyexchanger.data.settings.SettingsDataSource
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.mapper.toBalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConvertingItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

interface BalanceInteractor {
    val balancesFlow: Flow<List<BalanceItemModel>>
    suspend fun updateBalance(convertingItem: ConvertingItem)
}

class BalanceInteractorImpl(
    private val localDataSource: BalanceLocalDataSource,
    private val settingsDataSource: SettingsDataSource
) : BalanceInteractor {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _balancesFlow =
        localDataSource.getBalances()
            .filter { it.isNotEmpty() }
            .mapLatest { items -> items.map { item -> item.toBalanceItemModel() } }

    override val balancesFlow: Flow<List<BalanceItemModel>> = _balancesFlow
    override suspend fun updateBalance(convertingItem: ConvertingItem) {
        with(convertingItem) {
            val updatedSellBalance =
                (currentBalances.firstOrNull { it.type == sellCurrencyType }?.amount ?: 0.0) - sellAmount - fee
            val updatedReceiveBalance =
                (currentBalances.firstOrNull { it.type == receiveCurrencyType }?.amount ?: 0.0) + receiveAmount
            val sellItem = BalanceItem(
                type = sellCurrencyType,
                amount = updatedSellBalance,
                isBaseType = true
            )
            updateBalance(sellItem)

            val receiveItem = BalanceItem(
                type = receiveCurrencyType,
                amount = updatedReceiveBalance,
                isBaseType = false
            )
            updateBalance(receiveItem)
            settingsDataSource.addTransaction()
        }
    }

    private suspend fun updateBalance(item: BalanceItem) {
        localDataSource.insertBalance(item)
    }
}