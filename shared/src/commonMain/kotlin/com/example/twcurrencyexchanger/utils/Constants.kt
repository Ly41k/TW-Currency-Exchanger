package com.example.twcurrencyexchanger.utils

object Constants {
    const val BASE_URL: String = "https://developers.paysera.com/tasks/api/"
    const val CURRENCY_UPDATE_DELAY_BY_DEFAULT: Long = 1000000
    const val AMOUNT_BY_DEFAULT: Double = 1000.0
    const val FEE_PERCENT: Double = 0.7

    const val FREE_TRANSACTION: Int = 5
    const val TRANSACTION_COUNT_KEY: String = "transactionCountKey"
}