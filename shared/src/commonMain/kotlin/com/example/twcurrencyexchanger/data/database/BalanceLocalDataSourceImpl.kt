package com.example.twcurrencyexchanger.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.twcurrencyexchanger.Database
import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.mapper.booleanToLong
import com.example.twcurrencyexchanger.domain.mapper.toBalanceItem
import com.example.twcurrencyexchanger.utils.Constants.AMOUNT_BY_DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class BalanceLocalDataSourceImpl(database: Database) : BalanceLocalDataSource {

    private val queries = database.balanceQueries
    override fun getBalances(): Flow<List<BalanceItem>> {
        return queries.getBalances()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { balanceEntities -> supervisorScope { balanceEntities.map { it.toBalanceItem() } } }
    }

    override suspend fun insertBalance(balanceItem: BalanceItem) {
        return queries.insertBalanceEntity(
            type = balanceItem.type,
            amount = balanceItem.amount,
            is_base_type = balanceItem.isBaseType.booleanToLong()
        )
    }

    override suspend fun initLocalDataSource(item: ExchangeRatesItem) {
        withContext(Dispatchers.IO) {
            item.rates.map {
                insertBalance(
                    BalanceItem(
                        type = it.key,
                        amount = if (it.key == item.base) AMOUNT_BY_DEFAULT else 0.0,
                        isBaseType = it.key == item.base
                    )
                )
            }
        }
    }

    override suspend fun clearBalance() {
        queries.deleteAll()
    }
}