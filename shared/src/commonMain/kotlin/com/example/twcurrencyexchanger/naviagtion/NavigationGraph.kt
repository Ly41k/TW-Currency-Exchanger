package com.example.twcurrencyexchanger.naviagtion

import com.example.twcurrencyexchanger.compose.main.balance.BalanceScreen
import com.example.twcurrencyexchanger.compose.main.converter.ConverterScreen
import com.example.twcurrencyexchanger.compose.splash.SplashScreen
import com.example.twcurrencyexchanger.core.navigation.NavigationTree
import com.example.twcurrencyexchanger.naviagtion.tabs.BalanceTab
import com.example.twcurrencyexchanger.naviagtion.tabs.BottomConfiguration
import com.example.twcurrencyexchanger.naviagtion.tabs.ConverterTab
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

internal fun RootComposeBuilder.navigationGraph() {
    screen(NavigationTree.Splash.SplashScreen.name) { SplashScreen() }

    bottomNavigation(
        name = NavigationTree.Main.Dashboard.name,
        tabsNavModel = BottomConfiguration()
    ) {

        tab(BalanceTab()) {
            screen(name = NavigationTree.Main.BalanceScreen.name) { BalanceScreen() }
        }

        tab(ConverterTab()) {
            screen(name = NavigationTree.Main.ConverterScreen.name) { ConverterScreen() }
        }
    }
}
