package com.example.twcurrencyexchanger.presentarion.main.balance

import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.api.CurrencyRepository
import com.example.twcurrencyexchanger.core.di.Inject
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState
import kotlinx.coroutines.launch

class BalanceViewModel : BaseSharedViewModel<BalanceViewState, BalanceAction, BalanceEvent>(
    initialState = BalanceViewState()
) {

    private val currencyRepository: CurrencyRepository = Inject.instance()
    override fun obtainEvent(viewEvent: BalanceEvent) {
        when (viewEvent) {
            BalanceEvent.SettingClick -> {}
        }
    }

    init {
        viewModelScope.launch {
            val response = currencyRepository.getCurrencyExchangeRates()
            println(response)
        }
    }
}