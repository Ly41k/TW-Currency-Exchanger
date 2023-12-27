package com.example.twcurrencyexchanger.presentarion.main.balance.models

import com.example.twcurrencyexchanger.domain.mapper.limitDecimals

data class BalanceItemModel(
    val amount: Double,
    val type: String,
    val baseType: Boolean
) {

    fun getStringAmount(): String = limitDecimals(amount, 2)

}