package com.example.twcurrencyexchanger.presentarion.main.balance.models

data class BalanceViewState(

    val items: List<BalanceItemModel> = listOf(
        BalanceItemModel("1000.00", "EUR"),
        BalanceItemModel("999.00", "UAH"),
        BalanceItemModel("1000.00", "USD"),
    )
)