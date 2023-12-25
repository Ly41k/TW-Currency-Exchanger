package com.example.twcurrencyexchanger.domain.mapper

import com.example.twcurrencyexchanger.api.models.ExchangeRatesResponse
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem

interface CurrencyMapper {
    fun toExchangeRatesItem(response: ExchangeRatesResponse): ExchangeRatesItem
}