package com.example.twcurrencyexchanger.domain.interactors

import com.example.twcurrencyexchanger.data.settings.SettingsDataSource
import com.example.twcurrencyexchanger.utils.Constants.EACH_N_TRANSACTION
import com.example.twcurrencyexchanger.utils.Constants.FEE_PERCENT
import com.example.twcurrencyexchanger.utils.Constants.FREE_FIRST_TRANSACTIONS
import com.example.twcurrencyexchanger.utils.Constants.TRANSACTION_UP_N_FREE

interface ConverterInteractor {
    fun getFeeByAmount(amount: Double): Double
}

class ConverterInteractorImpl(
    private val settingsDataSource: SettingsDataSource,
) : ConverterInteractor {

    override fun getFeeByAmount(amount: Double): Double {
        val transactionCount = settingsDataSource.fetchTransactionCount()
        return when {
            amount <= 0 -> 0.0
            transactionCount < FREE_FIRST_TRANSACTIONS -> 0.0
            amount >= TRANSACTION_UP_N_FREE -> 0.0
            transactionCount % EACH_N_TRANSACTION == 0 -> 0.0
            else -> amount * FEE_PERCENT / 100
        }
    }
}