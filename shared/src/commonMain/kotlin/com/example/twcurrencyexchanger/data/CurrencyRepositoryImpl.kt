package com.example.twcurrencyexchanger.data

import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.core.store.ClearableBaseStore
import com.example.twcurrencyexchanger.data.ktor.KtorCurrencyRemoteDataSource
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.mapper.CurrencyMapper

class CurrencyRepositoryImpl(
    private val remoteDataSource: KtorCurrencyRemoteDataSource,
    private val currencyMapper: CurrencyMapper,
    private val currencyStore: ClearableBaseStore<ExchangeRatesItem>
) : CurrencyRepository {
    override suspend fun getCurrencyExchangeRates(): ExchangeRatesItem {
        val response = remoteDataSource.performGetCurrencyExchangeRates()
        val item = currencyMapper.toExchangeRatesItem(response)
        currencyStore.publish(item)
        return item
    }
}