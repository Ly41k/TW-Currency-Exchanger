package com.example.twcurrencyexchanger.presentarion.main.converter

import androidx.lifecycle.DefaultLifecycleObserver
import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.AppRes
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.core.store.ClearableBaseStore
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.domain.interactors.ConverterInteractor
import com.example.twcurrencyexchanger.domain.mapper.limitDecimals
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterAction
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterError
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterEvent
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterViewState
import com.example.twcurrencyexchanger.utils.ExchangeRatesUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val balanceInteractor: BalanceInteractor = Inject.instance(),
    private val exchangeRatesUpdater: ExchangeRatesUpdater = Inject.instance(),
    private val converterInteractor: ConverterInteractor = Inject.instance(),
    currencyStore: ClearableBaseStore<ExchangeRatesItem> = Inject.instance(),
) : BaseSharedViewModel<ConverterViewState, ConverterAction, ConverterEvent>(
    initialState = ConverterViewState()
), DefaultLifecycleObserver {

    private var converterJob: Job? = null
    override fun obtainEvent(viewEvent: ConverterEvent) {
        println("TESTING_TAG - viewEvent - $viewEvent")
        when (viewEvent) {
            is ConverterEvent.SelectedSellCurrencyChanged -> obtainSelectedSellCurrencyChanged(viewEvent.value)
            is ConverterEvent.SellAmountChanged -> obtainSellAmountChanged(viewEvent.value)
            ConverterEvent.SellCurrencyPickerStateChanged -> obtainSellCurrencyPickerStateChanged()
            ConverterEvent.SettingClick -> openSettings()
            ConverterEvent.ReceiveCurrencyPickerStateChanged -> obtainReceiveCurrencyPickerStateChanged()
            is ConverterEvent.SelectedReceiveCurrencyChanged -> obtainSelectedReceiveCurrencyChanged(viewEvent.value)
            ConverterEvent.SubmitClick -> performSubmitClick()
        }
    }

    init {
        startUpdater()
        observeBalances()
        currencyStore.observe()
            .onEach { viewState = viewState.copy(currencyRates = it.rates) }
            .launchIn(viewModelScope)
    }

    private fun performSubmitClick() {
        converterJob?.cancel()
        converterJob = viewModelScope.launch(Dispatchers.Default) {
            val sellAmount = viewState.sellAmount
            val currentBalance =
                viewState.items.firstOrNull { it.type == viewState.selectedSellCurrency }?.amount ?: 0.0
            val fee = converterInteractor.getFeeByAmount(sellAmount)

            when {
                sellAmount == 0.0 -> {
                    viewState = viewState.copy(
                        errorType = ConverterError.WrongAmount
                    )
                }

                sellAmount > currentBalance -> {
                    viewState = viewState.copy(
                        errorType = ConverterError.NotEnoughMoney
                    )
                }

                (sellAmount + fee) > currentBalance -> {
                    viewState = viewState.copy(
                        errorType = ConverterError.NotEnoughMoneyForFee
                    )
                }

                else -> {
                    viewState = viewState.copy(fee = fee)
                    openAlertDialog()
                }
            }
        }
    }

    private fun openAlertDialog() {
        val message = AppRes.string.alert_message.format(
            sellAmount = limitDecimals(viewState.sellAmount, 2),
            sellCurrencyType = viewState.selectedSellCurrency,
            receiveAmount = limitDecimals(viewState.receiveAmount, 2),
            receiveCurrencyType = viewState.selectedReceiveCurrency,
            feeAmount = limitDecimals(viewState.fee, 2),
            feeCurrencyType = viewState.selectedSellCurrency
        )
        viewAction = ConverterAction.OpenAlertDialog(message)
    }

    private fun obtainSellAmountChanged(value: String) {
        viewState = viewState.copy(
            sellAmount = value.toDoubleOrNull() ?: 0.0,
            receiveAmount = (value.toDoubleOrNull() ?: 0.0) * (viewState.currentRate?.rate ?: 1.0),
            errorType = null
        )
    }

    private fun openSettings() {
        viewAction = ConverterAction.OpenSettingsScreen
    }

    private fun startUpdater() {
        exchangeRatesUpdater.start()
    }

    private fun observeBalances() {
        balanceInteractor.balancesFlow
            .filter { it.isNotEmpty() }
            .onEach { obtainBalances(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun obtainBalances(balances: List<BalanceItemModel>) {
        viewState = viewState.copy(
            items = balances.filter { it.amount > 0 },
            sellCurrencyItems = balances.filter { it.amount > 0 }.map { it.type },
            selectedSellCurrency = balances.firstOrNull { it.baseType }?.type.orEmpty(),
            selectedReceiveCurrency = balances.firstOrNull { it.baseType }?.type.orEmpty(),
            receiveCurrencyItems = balances.map { it.type },
        )
    }

    private fun obtainSellCurrencyPickerStateChanged() {
        viewState = viewState.copy(isExpandedSellCurrencyList = !viewState.isExpandedSellCurrencyList)
    }

    private fun obtainReceiveCurrencyPickerStateChanged() {
        viewState = viewState.copy(isExpandedReceiveCurrencyList = !viewState.isExpandedReceiveCurrencyList)
    }

    private fun obtainSelectedSellCurrencyChanged(item: String) {
        viewState = viewState.copy(
            isExpandedSellCurrencyList = false,
            selectedSellCurrency = item,
            errorType = null
        )
    }

    private fun obtainSelectedReceiveCurrencyChanged(item: String) {
        viewState = viewState.copy(
            isExpandedReceiveCurrencyList = false,
            selectedReceiveCurrency = item,
            currentRate = viewState.currencyRates.firstOrNull { it.key == item },
            receiveAmount = viewState.currencyRates.firstOrNull { it.key == item }?.let {
                it.rate * viewState.sellAmount
            } ?: viewState.receiveAmount,
            errorType = null
        )
    }

    fun clearAction() {
        viewAction = null
    }
}