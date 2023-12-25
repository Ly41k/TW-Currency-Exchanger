package com.example.twcurrencyexchanger.api

import com.example.twcurrencyexchanger.domain.ExchangeRatesItem

interface CurrencyRepository {

    suspend fun getCurrencyExchangeRates() : ExchangeRatesItem
}