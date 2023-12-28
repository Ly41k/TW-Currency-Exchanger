package com.example.twcurrencyexchanger.compose.main.balance

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.example.twcurrencyexchanger.presentarion.main.balance.BalanceViewModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction

@Composable
fun BalanceScreen() {

    StoredViewModel(factory = { BalanceViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState().value
        val action = viewModel.viewActions().observeAsState().value

        BalanceView(state = state) { viewModel.obtainEvent(it) }

        when (action) {
            BalanceAction.OpenSettingsScreen -> {}
            null -> {}
        }
    }
}
