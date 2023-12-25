package com.example.twcurrencyexchanger.data

import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.data.ktor.KtorCurrencyRemoteDataSource
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.mapper.CurrencyMapper

class CurrencyRepositoryImpl(
    private val remoteDataSource: KtorCurrencyRemoteDataSource,
    private val currencyMapper: CurrencyMapper
) : CurrencyRepository {
    override suspend fun getCurrencyExchangeRates(): ExchangeRatesItem {
        val response = remoteDataSource.performGetCurrencyExchangeRates()
        return currencyMapper.toExchangeRatesItem(response)
    }
}