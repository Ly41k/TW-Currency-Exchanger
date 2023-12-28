package com.example.twcurrencyexchanger.presentarion.main.balance

import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceItemModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState
import com.example.twcurrencyexchanger.utils.ExchangeRatesUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class BalanceViewModel(
    private val balanceInteractor: BalanceInteractor = Inject.instance(),
    private val exchangeRatesUpdater: ExchangeRatesUpdater = Inject.instance()
) : BaseSharedViewModel<BalanceViewState, BalanceAction, BalanceEvent>(
    initialState = BalanceViewState()
) {

    override fun obtainEvent(viewEvent: BalanceEvent) {
        when (viewEvent) {
            BalanceEvent.SettingClick -> openSettings()
        }
    }

    init {
        startUpdater()
        observeBalances()
    }

    private fun startUpdater() {
        exchangeRatesUpdater.start()
    }

    private fun observeBalances() {
        balanceInteractor.balancesFlow
            .filter { it.isNotEmpty() }
            .map { it.filter { it.amount > 0 } }
            .onEach { obtainBalances(it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun obtainBalances(balances: List<BalanceItemModel>) {
        viewState = viewState.copy(items = balances)
    }

    private fun openSettings() {
        viewAction = BalanceAction.OpenSettingsScreen
    }
}