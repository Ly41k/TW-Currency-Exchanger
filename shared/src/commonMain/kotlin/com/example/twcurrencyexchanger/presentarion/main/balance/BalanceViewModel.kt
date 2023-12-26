package com.example.twcurrencyexchanger.presentarion.main.balance

import com.adeo.kviewmodel.BaseSharedViewModel
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceAction
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceEvent
import com.example.twcurrencyexchanger.presentarion.main.balance.models.BalanceViewState

class BalanceViewModel : BaseSharedViewModel<BalanceViewState, BalanceAction, BalanceEvent>(
    initialState = BalanceViewState()
) {
    override fun obtainEvent(viewEvent: BalanceEvent) {
        when (viewEvent) {
            BalanceEvent.SettingClick -> {}
        }
    }
}