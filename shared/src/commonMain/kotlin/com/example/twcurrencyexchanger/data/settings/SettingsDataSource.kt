package com.example.twcurrencyexchanger.data.settings

import com.example.twcurrencyexchanger.utils.Constants.TRANSACTION_COUNT_KEY
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class SettingsDataSource(private val settings: Settings) {

    fun addTransaction() {
        val count = fetchTransactionCount()
        settings.putInt(TRANSACTION_COUNT_KEY, count + 1)
    }

    fun fetchTransactionCount(): Int = settings[TRANSACTION_COUNT_KEY, 0]
}