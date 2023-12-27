package com.example.twcurrencyexchanger.ui.themes

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CurrencyExchangerColors(
    val primaryBackground: Color,
    val primaryAction: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val thirdTextColor: Color,
    val dividerColor: Color,
    val tagSellColor: Color,
    val tagReceiveColor: Color,
    val tagSellTextColor: Color,
    val tagReceiveTextColor: Color
)

val palette = CurrencyExchangerColors(
    primaryBackground = Color(0xFFFFFFFF),
    primaryAction = Color(0xFF058ACB),
    primaryTextColor = Color(0xFF111928),
    secondaryTextColor = Color(0xFF6B7280),
    thirdTextColor = Color(0xFFFFFFFF),
    dividerColor = Color(0xFFE5E7EB),
    tagSellColor = Color(0xFFF05252),
    tagReceiveColor = Color(0xFF0E9F6E),
    tagSellTextColor = Color(0xFF44A9F4),
    tagReceiveTextColor = Color(0xFF44A9F4)
)

val LocalColorProvider = staticCompositionLocalOf<CurrencyExchangerColors> { error("No default implementation") }