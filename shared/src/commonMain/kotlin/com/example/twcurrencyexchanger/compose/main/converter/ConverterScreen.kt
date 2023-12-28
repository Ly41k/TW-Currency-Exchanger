package com.example.twcurrencyexchanger.compose.main.converter

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.example.twcurrencyexchanger.presentarion.main.converter.ConverterViewModel
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterAction
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConverterScreen() {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()
    val alertConfiguration = AlertConfiguration(maxWidth = 0.7f, cornerRadius = 10)
    val keyboardController = LocalSoftwareKeyboardController.current

    StoredViewModel(factory = { ConverterViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState().value
        val action = viewModel.viewActions().observeAsState().value

        ConverterView(state = state) { viewModel.obtainEvent(it) }

        when (action) {
            ConverterAction.OpenSettingsScreen -> {}
            is ConverterAction.OpenAlertDialog -> {
                keyboardController?.hide()
                modalController.present(alertConfiguration) { key ->
                    AlertDialogScreen(message = action.message) {
                        viewModel.clearAction()
                        modalController.popBackStack(key)
                    }
                }
            }

            null -> {}
        }
    }
}

