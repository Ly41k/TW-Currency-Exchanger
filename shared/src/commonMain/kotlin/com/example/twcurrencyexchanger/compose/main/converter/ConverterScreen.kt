package com.example.twcurrencyexchanger.compose.main.converter

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.example.twcurrencyexchanger.presentarion.main.converter.ConverterViewModel
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterAction
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun ConverterScreen() {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { ConverterViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState().value
        val action = viewModel.viewActions().observeAsState().value

        ConverterView(state = state) { viewModel.obtainEvent(it) }

        when (action) {
            null -> {}
            ConverterAction.OpenSettingsScreen -> {}
        }
    }
}