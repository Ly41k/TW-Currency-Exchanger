package com.example.twcurrencyexchanger.domain.mapper

import com.example.twcurrencyexchanger.api.models.ExchangeRatesResponse
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.RateItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class CurrencyMapperImpl(private val json: Json) : CurrencyMapper {
    override fun toExchangeRatesItem(response: ExchangeRatesResponse): ExchangeRatesItem {
        val rates = json.encodeToJsonElement(response.rates).jsonObject.toMap()
            .mapNotNull { map ->
                map.value.toString().toDoubleOrNull()?.let { rate -> RateItem(key = map.key, rate = rate) }
            }
        return ExchangeRatesItem(
            base = response.base.orEmpty(),
            date = response.date.orEmpty(),
            rates = rates
        )
    }
}

