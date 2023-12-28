package com.example.twcurrencyexchanger.core.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

internal class HttpEngineFactory {
    fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Android
}