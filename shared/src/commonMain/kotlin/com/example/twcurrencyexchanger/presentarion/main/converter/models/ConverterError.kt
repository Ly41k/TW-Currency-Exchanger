package com.example.twcurrencyexchanger.presentarion.main.converter.models


enum class ConverterError(val error: String) {
    NotEnoughMoney("Not enough money"),
    NotEnoughMoneyForFee("Not enough money for the fee"),
    WrongAmount("Wrong amount"),
    ConvertingError("Error")
}
