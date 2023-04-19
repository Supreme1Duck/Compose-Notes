package com.example.duck.fastnotes.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
    VM used by each screen in login flow
*/
abstract class WelcomeBaseViewModel<UIState>(
    initialUiState: UIState
): ViewModel(), WelcomeScreensVMDelegate<UIState> {

    private val uiState = MutableStateFlow(initialUiState)
    override val state: StateFlow<UIState> = uiState.asStateFlow()

    override val currentState: UIState
        get() = uiState.value

    private val uiEvent = Channel<ScreenStatus>()
    override val event: Flow<ScreenStatus> = uiEvent.receiveAsFlow()

    fun reduce(action: (UIState) -> UIState) {
        viewModelScope.launch {
            uiState.update { action(currentState) }
        }
    }

    suspend fun sendEvent(screenEvent: ScreenStatus) {
        uiEvent.send(screenEvent)
    }

    override fun onButtonClick() {
        viewModelScope.launch {
            if (validate())
                uiEvent.send(ScreenStatus.Success)
            else {
                uiEvent.send(ScreenStatus.Failure)
            }
        }
    }

    abstract override suspend fun validate(): Boolean
}