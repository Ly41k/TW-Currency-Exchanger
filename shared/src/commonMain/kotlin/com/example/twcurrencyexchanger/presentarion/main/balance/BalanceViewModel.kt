package com.example.twcurrencyexchanger.presentarion.main.balance

import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.domain.interactors.BalanceInteractor
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BalanceViewModel : BaseSharedViewModel<BalanceViewState, BalanceAction, BalanceEvent>(
    initialState = BalanceViewState()
) {

    private val balanceInteractor: BalanceInteractor = Inject.instance()
    override fun obtainEvent(viewEvent: BalanceEvent) {
        when (viewEvent) {
            BalanceEvent.SettingClick -> {}
        }
    }

    init {
        balanceInteractor.balancesFlow
            .filter { it.isNotEmpty() }
            .onEach {
                println("TESTING_TAG - ${it.size}")
                viewState = viewState.copy(items = it)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)


    }
}