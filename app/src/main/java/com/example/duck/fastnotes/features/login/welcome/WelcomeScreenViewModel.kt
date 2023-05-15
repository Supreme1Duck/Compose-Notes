package com.example.duck.fastnotes.features.login.welcome

import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel
import com.example.duck.fastnotes.utils.ui.ObserverUtils.call
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor() : WelcomeBaseViewModel<WelcomeScreenState>(
    initialUiState = WelcomeScreenState(false)
) {

    val errorNotCheckedEvent = Channel<Unit>()

    fun setCheckedState(checked: Boolean) {
        reduce { state ->
            state.copy(isAgreementChecked = checked)
        }
    }

    override suspend fun validate(): Boolean {
        return if (currentState.isAgreementChecked) {
            true
        } else {
            errorNotCheckedEvent.call()
            false
        }
    }
}

data class WelcomeScreenState(
    val isAgreementChecked: Boolean
)

