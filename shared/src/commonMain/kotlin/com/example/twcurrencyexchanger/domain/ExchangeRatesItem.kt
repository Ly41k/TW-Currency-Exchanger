package com.example.twcurrencyexchanger.domain

data class ExchangeRatesItem(
    val base: String = "",
    val date: String = "",
    val rates: List<RateItem> = emptyList()
)
