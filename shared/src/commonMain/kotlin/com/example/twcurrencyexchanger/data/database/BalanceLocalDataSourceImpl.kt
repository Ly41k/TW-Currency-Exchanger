package com.example.twcurrencyexchanger.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.twcurrencyexchanger.Database
import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import com.example.twcurrencyexchanger.domain.BalanceLocalDataSource
import com.example.twcurrencyexchanger.domain.mapper.booleanToLong
import com.example.twcurrencyexchanger.domain.mapper.toBalanceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

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
            id = balanceItem.id,
            type = balanceItem.type,
            amount = balanceItem.amount,
            is_base_type = balanceItem.isBaseType.booleanToLong()
        )
    }

    override suspend fun clearBalance() {
        queries.deleteAll()
    }
}