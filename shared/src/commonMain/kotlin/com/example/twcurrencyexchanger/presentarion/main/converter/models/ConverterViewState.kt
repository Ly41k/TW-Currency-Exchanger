package com.example.twcurrencyexchanger.presentarion.main.converter.models

import com.example.twcurrencyexchanger.domain.RateItem
import com.example.twcurrencyexchanger.domain.mapper.limitDecimals
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel

data class ConverterViewState(
    val items: List<BalanceItemModel> = emptyList(),
    val selectedSellCurrency: String = "",
    val selectedReceiveCurrency: String = "",
    val sellAmount: Double = 0.0,
    val receiveAmount: Double = 0.0,
    val fee: Double = 0.0,
    val sellCurrencyItems: List<String> = emptyList(),
    val receiveCurrencyItems: List<String> = emptyList(),
    val isExpandedSellCurrencyList: Boolean = false,
    val isExpandedReceiveCurrencyList: Boolean = false,
    val currencyRates: List<RateItem> = emptyList(),
    val currentRate: RateItem? = null,
    val errorType: ConverterError? = null,
) {
    fun getReceiveAmountFormatted(): String = limitDecimals(receiveAmount, 2)

    fun getSellAmountFormatted(): String = limitDecimals(sellAmount, 2)
}