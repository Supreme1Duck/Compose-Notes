package com.example.duck.fastnotes.features.login.welcome

import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel

class WelcomeScreenViewModel : WelcomeBaseViewModel<WelcomeScreenState>(
    initialUiState = WelcomeScreenState(false)
) {
    fun setCheckedState(checked: Boolean) {
        reduce { state ->
            state.copy(isAgreementChecked = checked)
        }
    }

    override fun validate(): Boolean {
        return state.value.isAgreementChecked
    }
}

data class WelcomeScreenState(
    val isAgreementChecked: Boolean
)

