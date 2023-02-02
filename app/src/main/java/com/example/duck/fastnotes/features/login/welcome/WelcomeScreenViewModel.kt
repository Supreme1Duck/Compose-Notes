package com.example.duck.fastnotes.features.login.welcome

import android.util.Log
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel

class WelcomeScreenViewModel : WelcomeBaseViewModel<WelcomeScreenState>(
    initialUiState = WelcomeScreenState(false)
) {
    fun setCheckedState(checked: Boolean) {
        asyncReduce { state ->
            state.copy(isAgreementChecked = checked)
        }
    }

    override fun validate(): Boolean {
        Log.d("DDebug", "Is checked = ${state.value.isAgreementChecked}")
        return state.value.isAgreementChecked
    }
}

data class WelcomeScreenState(
    val isAgreementChecked: Boolean
)

