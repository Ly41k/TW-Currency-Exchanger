package com.example.twcurrencyexchanger.compose.main.balance

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState
import com.example.twcurrencyexchanger.ui.components.BalanceItemView
import com.example.twcurrencyexchanger.ui.components.TopAppBarView
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun BalanceView(
    state: BalanceViewState,
    eventHandler: (BalanceEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarView { eventHandler(BalanceEvent.SettingClick) } },
    ) { padding ->

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(
                    text = AppRes.string.my_balance.uppercase(),
                    color = Theme.colors.secondaryTextColor,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            items(state.items) { BalanceItemView(it) }
        }
    }
}