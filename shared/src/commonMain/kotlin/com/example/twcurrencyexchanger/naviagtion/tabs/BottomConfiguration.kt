package com.example.twcurrencyexchanger.naviagtion.tabs

import androidx.compose.runtime.Composable
import com.example.twcurrencyexchanger.ui.themes.Theme
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel

class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {
    override val navConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Theme.colors.primaryBackground,
                selectedColor = Theme.colors.primaryAction,
                unselectedColor = Theme.colors.secondaryTextColor
            )
        }
}