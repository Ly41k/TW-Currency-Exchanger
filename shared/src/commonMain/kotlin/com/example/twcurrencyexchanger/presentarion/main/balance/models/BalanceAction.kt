package com.example.twcurrencyexchanger.presentarion.main.balance.models

sealed class BalanceAction {
    data object OpenSettingsScreen : BalanceAction()
}