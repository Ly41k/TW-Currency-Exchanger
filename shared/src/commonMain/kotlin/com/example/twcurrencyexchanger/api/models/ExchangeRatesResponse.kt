package com.example.twcurrencyexchanger.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesResponse(
    @SerialName("base") val base: String?,
    @SerialName("date") val date: String?,
    @SerialName("rates") val rates: RatesResponse?
)