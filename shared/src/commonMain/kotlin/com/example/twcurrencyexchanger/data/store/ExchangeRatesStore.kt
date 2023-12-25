package com.example.twcurrencyexchanger.data.store

import com.example.twcurrencyexchanger.core.store.ClearableBaseStore
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem

class ExchangeRatesStore : ClearableBaseStore<ExchangeRatesItem>(
    getInitialValue = { ExchangeRatesItem() },
    getClearValue = { ExchangeRatesItem() }
)