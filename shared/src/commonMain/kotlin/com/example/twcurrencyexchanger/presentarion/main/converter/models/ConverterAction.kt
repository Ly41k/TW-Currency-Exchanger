package com.example.twcurrencyexchanger.presentarion.main.converter.models

sealed class ConverterAction {
    data object OpenSettingsScreen : ConverterAction()
    data class OpenAlertDialog(val message: String) : ConverterAction()
}