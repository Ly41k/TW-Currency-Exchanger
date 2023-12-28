package com.example.twcurrencyexchanger.presentarion.main.converter.models

sealed class ConverterEvent {
    data object SettingClick : ConverterEvent()
    data class SellAmountChanged(val value: String) : ConverterEvent()
    data object SellCurrencyPickerStateChanged : ConverterEvent()
    data class SelectedReceiveCurrencyChanged(val value: String) : ConverterEvent()
    data class SelectedSellCurrencyChanged(val value: String) : ConverterEvent()
    data object ReceiveCurrencyPickerStateChanged : ConverterEvent()
    data object SubmitClick : ConverterEvent()
}
