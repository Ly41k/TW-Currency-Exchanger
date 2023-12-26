package com.example.twcurrencyexchanger.android

import android.app.Application
import com.example.twcurrencyexchanger.core.di.PlatformConfiguration
import com.example.twcurrencyexchanger.core.di.PlatformSDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }

    private fun App.initPlatformSDK() {
        PlatformSDK.init(configuration = PlatformConfiguration(applicationContext = applicationContext))
    }
}