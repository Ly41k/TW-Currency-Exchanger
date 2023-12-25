package com.example.twcurrencyexchanger.naviagtion.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.twcurrencyexchanger.ui.themes.Theme
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class BalanceTab : TabItem() {
    override val configuration: TabConfiguration
        @Composable
        get() {

            return TabConfiguration(
                title = "Balance",
                selectedColor = Theme.colors.primaryAction,
                unselectedColor = Theme.colors.secondaryTextColor,
                titleStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                selectedIcon = rememberVectorPainter(image = Icons.Filled.Home),
                unselectedIcon = rememberVectorPainter(image = Icons.Outlined.Home)
            )
        }
}

class ConverterTab : TabItem() {
    override val configuration: TabConfiguration
        @Composable
        get() {

            return TabConfiguration(
                title = "Convert",
                selectedColor = Theme.colors.primaryAction,
                unselectedColor = Theme.colors.secondaryTextColor,
                titleStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                selectedIcon = rememberVectorPainter(image = Icons.Filled.AddCircle),
                unselectedIcon = rememberVectorPainter(image = Icons.Outlined.AddCircle)
            )
        }
}