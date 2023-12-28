package com.example.twcurrencyexchanger.compose.main.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun AlertDialogScreen(
    message: String,
    onCloseClick: () -> Unit,
) {
    Column(
        modifier = Modifier.wrapContentSize()
            .background(Theme.colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = AppRes.string.currency_converted,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = Theme.colors.primaryTextColor
        )

        Text(
            modifier = Modifier.padding(start = 28.dp, top = 8.dp, bottom = 16.dp, end = 28.dp),
            text = message,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Theme.colors.primaryTextColor
        )

        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = Theme.colors.dividerColor
        )

        Box(
            modifier = Modifier.fillMaxWidth().height(48.dp).clickable { onCloseClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = AppRes.string.done,
                color = Color.Blue,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}