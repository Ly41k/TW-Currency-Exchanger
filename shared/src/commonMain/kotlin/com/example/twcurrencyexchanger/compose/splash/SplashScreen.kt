package com.example.twcurrencyexchanger.compose.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.twcurrencyexchanger.core.navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
internal fun SplashScreen() {
    val rootController = LocalRootController.current

    LaunchedEffect(Unit) {
        rootController.present(NavigationTree.Main.Dashboard.name, launchFlag = LaunchFlag.SingleNewTask)
    }
}