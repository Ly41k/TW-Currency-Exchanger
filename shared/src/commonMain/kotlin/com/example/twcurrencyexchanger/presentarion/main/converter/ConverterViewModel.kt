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
import com.example.twcurrencyexchanger.presentarion.main.converter.models.ConvertingItem
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
                sellAmount == 0.0 -> viewState = viewState.copy(errorType = ConverterError.WrongAmount)

                viewState.selectedSellCurrency == viewState.selectedReceiveCurrency ->
                    viewState = viewState.copy(errorType = ConverterError.WrongCurrency)

                sellAmount > currentBalance ->
                    viewState = viewState.copy(errorType = ConverterError.NotEnoughMoney)

                (sellAmount + fee) > currentBalance ->
                    viewState = viewState.copy(errorType = ConverterError.NotEnoughMoneyForFee)

                else -> {
                    viewState = viewState.copy(fee = fee)
                    performConverting()
                }
            }
        }
    }

    private suspend fun performConverting() {
        val convertingItem = ConvertingItem(
            currentBalances = viewState.items,
            sellAmount = viewState.sellAmount,
            sellCurrencyType = viewState.selectedSellCurrency,
            receiveAmount = viewState.receiveAmount,
            receiveCurrencyType = viewState.selectedReceiveCurrency,
            fee = viewState.fee
        )
        balanceInteractor.updateBalance(convertingItem)
        openAlertDialog(convertingItem)
    }

    private fun openAlertDialog(item: ConvertingItem) {
        val message = AppRes.string.alert_message.format(
            sellAmount = limitDecimals(item.sellAmount, 2),
            sellCurrencyType = item.sellCurrencyType,
            receiveAmount = limitDecimals(item.receiveAmount, 2),
            receiveCurrencyType = item.receiveCurrencyType,
            feeAmount = limitDecimals(item.fee, 2),
            feeCurrencyType = item.sellCurrencyType
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
            items = balances.sortedBy { !it.baseType },
            sellCurrencyItems = balances.filter { it.baseType }.map { it.type },
            selectedSellCurrency = balances.firstOrNull { it.baseType }?.type.orEmpty(),
            selectedReceiveCurrency = if (viewState.selectedReceiveCurrency.isEmpty())
                balances.firstOrNull { it.baseType }?.type.orEmpty()
            else viewState.selectedReceiveCurrency,
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