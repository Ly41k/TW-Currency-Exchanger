package com.example.twcurrencyexchanger.domain.interactors

import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.mapper.toBalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

interface BalanceInteractor {
    val balancesFlow: Flow<List<BalanceItemModel>>
}

class BalanceInteractorImpl(
    localDataSource: BalanceLocalDataSource
) : BalanceInteractor {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _balancesFlow =
        localDataSource.getBalances()
            .filter { it.isNotEmpty() }
            .mapLatest { items ->
                items.filter { item -> item.amount > 0 }
                    .map { item -> item.toBalanceItemModel() }
            }

    override val balancesFlow: Flow<List<BalanceItemModel>> = _balancesFlow


}