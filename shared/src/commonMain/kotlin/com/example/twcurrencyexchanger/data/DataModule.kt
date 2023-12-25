package com.example.twcurrencyexchanger.data

import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.core.store.ClearableBaseStore
import com.example.twcurrencyexchanger.data.ktor.KtorCurrencyRemoteDataSource
import com.example.twcurrencyexchanger.data.store.ExchangeRatesStore
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val dataModule = DI.Module("dataModule") {
    bind<CurrencyRepository>() with singleton {
        CurrencyRepositoryImpl(instance(), instance())
    }

    bind<KtorCurrencyRemoteDataSource>() with provider {
        KtorCurrencyRemoteDataSource(instance())
    }

    bind<ClearableBaseStore<ExchangeRatesItem>>() with singleton { ExchangeRatesStore() }
}