package com.example.twcurrencyexchanger.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun ConverterBalanceItemView(model: BalanceItemModel) {
    Row(modifier = Modifier.wrapContentSize().padding(horizontal = 16.dp)) {
        Text(
            text = model.getStringAmount(),
            color = Theme.colors.primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = model.type,
            color = Theme.colors.primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}