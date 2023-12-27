package com.example.twcurrencyexchanger.domain

import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import kotlinx.coroutines.flow.Flow

interface BalanceLocalDataSource {
    fun getBalances(): Flow<List<BalanceItem>>
    suspend fun insertBalance(balanceItem: BalanceItem)
    suspend fun clearBalance()
}