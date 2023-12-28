package com.example.twcurrencyexchanger.domain.interactors

import com.example.twcurrencyexchanger.data.settings.SettingsDataSource
import com.example.twcurrencyexchanger.utils.Constants.FEE_PERCENT
import com.example.twcurrencyexchanger.utils.Constants.FREE_TRANSACTION

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
            transactionCount < FREE_TRANSACTION -> 0.0
            else -> amount * FEE_PERCENT / 100
        }
    }
}