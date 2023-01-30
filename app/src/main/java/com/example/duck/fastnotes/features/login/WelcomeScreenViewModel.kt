package com.example.duck.fastnotes.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.features.login.WelcomeNavigationViewModel.Companion.buttonClickCallback
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WelcomeScreenViewModel : ViewModel() {

    init {
        buttonClickCallback.clicked
            .onEach { onButtonClick() }
            .launchIn(viewModelScope)
    }

    private val _event = Channel<ScreenStatus>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(WelcomeScreenStateCh(isAgreementChecked = false))
    val state = _state.asStateFlow()

    fun setCheckedState(checked: Boolean) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isAgreementChecked = checked))
        }
    }

    private fun validate(state: WelcomeScreenStateCh): Boolean = state.isAgreementChecked

    private fun onButtonClick() {
        viewModelScope.launch {
            if (validate(_state.value)) {
                _event.send(ScreenStatus.Success)
            } else {
                _event.send(ScreenStatus.Failure)
            }
        }
    }
}

data class WelcomeScreenStateCh(
    val isAgreementChecked: Boolean
)

sealed interface ScreenStatus {
    object Success: ScreenStatus
    object Failure: ScreenStatus
}

