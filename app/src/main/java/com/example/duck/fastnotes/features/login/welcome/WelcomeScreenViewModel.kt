package com.example.duck.fastnotes.features.login.welcome

import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor() : WelcomeBaseViewModel<WelcomeScreenState>(
    initialUiState = WelcomeScreenState(false)
) {
    fun setCheckedState(checked: Boolean) {
        reduce { state ->
            state.copy(isAgreementChecked = checked)
        }
    }

    override suspend fun validate(): Boolean {
        return state.value.isAgreementChecked
    }
}

data class WelcomeScreenState(
    val isAgreementChecked: Boolean
)

