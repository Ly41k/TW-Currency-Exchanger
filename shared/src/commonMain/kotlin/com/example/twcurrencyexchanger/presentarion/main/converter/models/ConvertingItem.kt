package com.example.twcurrencyexchanger.presentarion.main.converter.models

import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel

data class ConvertingItem(
    val currentBalances: List<BalanceItemModel>,
    val sellAmount: Double,
    val sellCurrencyType: String,
    val receiveAmount: Double,
    val receiveCurrencyType: String,
    val fee: Double
)