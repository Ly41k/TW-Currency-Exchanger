package com.example.twcurrencyexchanger.core.di

import com.example.twcurrencyexchanger.core.ktor.ktorModule
import com.example.twcurrencyexchanger.core.settings.settingsModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton

object PlatformSDK {

    fun init(
        configuration: PlatformConfiguration
    ) {
        val coreModule = DI.Module("coreModule") {
            bind<PlatformConfiguration>() with singleton { configuration }
        }

        Inject.createDependencies(
            DI {
                importAll(
                    coreModule,
                    ktorModule,
                    settingsModule
                )
            }.direct
        )
    }
}