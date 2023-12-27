package com.example.twcurrencyexchanger.data.database.models

data class BalanceItem(
    val id: Long? = null,
    val type: String,
    val amount: Double,
    val isBaseType: Boolean
)