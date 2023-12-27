package com.example.twcurrencyexchanger.domain

import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractorImpl
import com.example.twcurrencyexchanger.domain.mapper.CurrencyMapper
import com.example.twcurrencyexchanger.domain.mapper.CurrencyMapperImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val domainModule = DI.Module("domainModule") {
    bind<CurrencyMapper>() with provider { CurrencyMapperImpl(instance()) }

    bind<BalanceInteractor>() with singleton { BalanceInteractorImpl(instance()) }
}