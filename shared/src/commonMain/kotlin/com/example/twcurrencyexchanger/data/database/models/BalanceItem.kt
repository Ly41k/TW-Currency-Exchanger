package com.example.twcurrencyexchanger.data.database.models

data class BalanceItem(
    val type: String,
    val amount: Double,
    val isBaseType: Boolean
)