package com.example.twcurrencyexchanger.compose.main.balance

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.twcurrencyexchanger.ui.components.TopAppBarView

@Composable
fun BalanceScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarView(onSettingButtonClick = {}) },
    ) {
        Text(text = "BalanceScreen", color = Color.Red)
    }
}



