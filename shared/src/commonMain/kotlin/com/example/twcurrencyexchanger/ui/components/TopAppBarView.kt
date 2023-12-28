package com.example.twcurrencyexchanger.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun TopAppBarView(
    modifier: Modifier = Modifier,
    onSettingButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppRes.string.app_name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Theme.colors.thirdTextColor
                )
            }
        },
        modifier = modifier,
        backgroundColor = Theme.colors.primaryAction,
        actions = {
//            IconButton(onClick = onSettingButtonClick) {
//                Icon(
//                    imageVector = Icons.Default.Settings,
//                    contentDescription = AppRes.string.settings,
//                    tint = Theme.colors.primaryBackground
//                )
//            }
        }
    )
}