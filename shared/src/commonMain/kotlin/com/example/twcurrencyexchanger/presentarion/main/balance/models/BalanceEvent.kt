package com.example.twcurrencyexchanger.presentarion.main.balance.models

sealed class BalanceEvent {
    data object SettingClick : BalanceEvent()

}