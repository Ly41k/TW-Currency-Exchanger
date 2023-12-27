package com.example.twcurrencyexchanger.presentarion.main.converter

import androidx.lifecycle.DefaultLifecycleObserver
import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.core.store.ClearableBaseStore
import com.example.twcurrencyexchanger.domain.ExchangeRatesItem
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterAction
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterEvent
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConverterViewState
import com.example.twcurrencyexchanger.utils.ExchangeRatesUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConverterViewModel(
    private val balanceInteractor: BalanceInteractor = Inject.instance(),
    private val exchangeRatesUpdater: ExchangeRatesUpdater = Inject.instance(),
    currencyStore: ClearableBaseStore<ExchangeRatesItem> = Inject.instance(),
) : BaseSharedViewModel<ConverterViewState, ConverterAction, ConverterEvent>(
    initialState = ConverterViewState()
), DefaultLifecycleObserver {
    override fun obtainEvent(viewEvent: ConverterEvent) {
        println("TESTING_TAG - viewEvent - $viewEvent")
        when (viewEvent) {
            is ConverterEvent.SelectedSellCurrencyChanged -> obtainSelectedSellCurrencyChanged(viewEvent.value)
            is ConverterEvent.SellAmountChanged -> obtainSellAmountChanged(viewEvent.value)
            ConverterEvent.SellCurrencyPickerStateChanged -> obtainSellCurrencyPickerStateChanged()
            ConverterEvent.SettingClick -> openSettings()
            ConverterEvent.ReceiveCurrencyPickerStateChanged -> obtainReceiveCurrencyPickerStateChanged()
            is ConverterEvent.SelectedReceiveCurrencyChanged -> obtainSelectedReceiveCurrencyChanged(viewEvent.value)
        }
    }

    init {
        startUpdater()
        observeBalances()
        currencyStore.observe()
            .onEach { viewState = viewState.copy(currencyRates = it.rates) }
            .launchIn(viewModelScope)
    }

    private fun obtainSellAmountChanged(value: String) {
        viewState = viewState.copy(
            sellAmount = value.toDoubleOrNull() ?: 0.0,
            receiveAmount = (value.toDoubleOrNull() ?: 0.0) * (viewState.currentRate?.rate ?: 1.0)
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
            selectedSellCurrency = item
        )
    }

    private fun obtainSelectedReceiveCurrencyChanged(item: String) {
        viewState = viewState.copy(
            isExpandedReceiveCurrencyList = false,
            selectedReceiveCurrency = item,
            currentRate = viewState.currencyRates.firstOrNull { it.key == item },
            receiveAmount = viewState.currencyRates.firstOrNull { it.key == item }?.let {
                it.rate * viewState.sellAmount
            } ?: viewState.receiveAmount
        )
    }
}