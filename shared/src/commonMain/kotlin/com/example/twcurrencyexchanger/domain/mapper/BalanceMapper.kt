package com.example.twcurrencyexchanger.domain.mapper

import com.example.twcurrencyexchanger.data.database.models.BalanceItem
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import databases.BalanceEntity
import kotlin.math.min

fun BalanceEntity.toBalanceItem(): BalanceItem =
    BalanceItem(id = id, type = type, amount = amount, isBaseType = is_base_type.longToBoolean())

fun BalanceItem.toBalanceItemModel(): BalanceItemModel {
    return BalanceItemModel(
        amount = limitDecimals(this.amount, 2),
        type = this.type
    )
}

fun Boolean.booleanToLong(): Long = if (this) 1 else 0
fun Long.longToBoolean(): Boolean = this > 0

fun <T> limitDecimals(input: T, maxDecimals: Int): String {
    val result = input.toString()
    val lastIndex = result.length - 1
    var pos = lastIndex
    while (pos >= 0 && result[pos] != '.') {
        pos--
    }
    return if (maxDecimals < 1 && pos >= 0) {
        result.substring(0, min(pos, result.length))
    } else if (pos >= 0) {
        result.substring(0, min(pos + 1 + maxDecimals, result.length))
    } else {
        return result
    }
}