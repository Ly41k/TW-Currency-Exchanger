package com.example.twcurrencyexchanger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.ui.themes.Theme

@Composable
fun ConvertItemView(
    isSellCurrency: Boolean,
    amount: String,
    selectedCurrencyType: String,
    currencyItems: List<String>,
    isExpandedCurrencyList: Boolean,
    onAmountChanged: (String) -> Unit,
    onSelectedCurrencyChanged: (String) -> Unit,
    onCurrencyPickerStateChanged: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50))
                .background(if (isSellCurrency) Theme.colors.tagSellColor else Theme.colors.tagReceiveColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = if (isSellCurrency) AppRes.string.sell else AppRes.string.receive,
                modifier = Modifier.size(25.dp).rotate(if (isSellCurrency) 90f else 270f),
                tint = Theme.colors.primaryBackground
            )
        }

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = if (isSellCurrency) AppRes.string.sell else AppRes.string.receive,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier.weight(1f),
            value = amount,
            maxLines = 1,
            enabled = isSellCurrency,
            onValueChange = { onAmountChanged(it) },
            textStyle = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Theme.colors.primaryTextColor,
                cursorColor = Color.Red,
                backgroundColor = Theme.colors.primaryBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Theme.colors.primaryTextColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        Box(modifier = Modifier.width(70.dp).background(Theme.colors.primaryBackground)) {
            Row(modifier = Modifier.padding(vertical = 16.dp).clickable {
                onCurrencyPickerStateChanged()
            }) {
                Text(
                    text = selectedCurrencyType,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }

            DropdownMenu(
                expanded = isExpandedCurrencyList,
                onDismissRequest = { onCurrencyPickerStateChanged() },
                modifier = Modifier.width(70.dp)
            ) {
                currencyItems.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier,
                        onClick = { onSelectedCurrencyChanged(item) },
                    ) {
                        Text(text = item, color = Theme.colors.primaryTextColor)
                    }
                }
            }
        }
    }
}
