package com.example.twcurrencyexchanger.compose.main.converter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterEvent
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterViewState
import com.example.twcurrencyexchanger.ui.components.ConvertItemView
import com.example.twcurrencyexchanger.ui.components.ConverterBalanceItemView
import com.example.twcurrencyexchanger.ui.components.TopAppBarView
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun ConverterView(
    state: ConverterViewState,
    eventHandler: (ConverterEvent) -> Unit

) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarView { eventHandler(ConverterEvent.SettingClick) } },
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

            item { LazyRow { items(state.items) { ConverterBalanceItemView(it) } } }

            item {
                Text(
                    text = AppRes.string.currency_exchange.uppercase(),
                    color = Theme.colors.secondaryTextColor,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            item {
                ConvertItemView(
                    isSellCurrency = true,
                    amount = state.getSellAmountFormatted(),
                    selectedCurrencyType = state.selectedSellCurrency,
                    currencyItems = state.sellCurrencyItems,
                    isExpandedCurrencyList = state.isExpandedSellCurrencyList,
                    onAmountChanged = { eventHandler(ConverterEvent.SellAmountChanged(it)) },
                    onSelectedCurrencyChanged = {
                        eventHandler(ConverterEvent.SelectedSellCurrencyChanged(it))
                    },
                    onCurrencyPickerStateChanged = {
                        eventHandler(ConverterEvent.SellCurrencyPickerStateChanged)
                    }
                )
            }
            item {
                Divider(
                    modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 24.dp),
                    color = Theme.colors.dividerColor
                )
            }

            item {
                ConvertItemView(
                    isSellCurrency = false,
                    amount = state.getReceiveAmountFormatted(),
                    selectedCurrencyType = state.selectedReceiveCurrency,
                    currencyItems = state.receiveCurrencyItems,
                    isExpandedCurrencyList = state.isExpandedReceiveCurrencyList,
                    onAmountChanged = { },
                    onSelectedCurrencyChanged = {
                        eventHandler(ConverterEvent.SelectedReceiveCurrencyChanged(it))
                    },
                    onCurrencyPickerStateChanged = {
                        eventHandler(ConverterEvent.ReceiveCurrencyPickerStateChanged)
                    }
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp),
                    contentAlignment = Alignment.Center
                ) {

                    state.errorType?.error?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Theme.colors.tagSellColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }


            item {
                Button(
                    onClick = { eventHandler(ConverterEvent.SubmitClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Theme.colors.primaryAction,
                        contentColor = Theme.colors.thirdTextColor
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Submit".uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}
