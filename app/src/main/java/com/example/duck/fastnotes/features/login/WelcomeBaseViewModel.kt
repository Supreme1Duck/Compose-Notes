package com.example.duck.fastnotes.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.features.login.button.ButtonClickReceiver
import com.example.duck.fastnotes.features.login.navigation.WelcomeNavigationViewModel.Companion.buttonClickCallback
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class WelcomeBaseViewModel<UIState>(
    initialUiState: UIState
): ViewModel(), WelcomeScreensVMDelegate<UIState> {

    private val buttonClickAction: ButtonClickReceiver = buttonClickCallback

    init {
        buttonClickAction.clicked
            .onEach {
                onButtonClick()
            }.launchIn(viewModelScope)
    }

    private val uiState = MutableStateFlow(initialUiState)
    override val state: StateFlow<UIState> = uiState.asStateFlow()

    override val currentState: UIState = uiState.value

    private val uiEvent = Channel<ScreenStatus>()
    override val event: Flow<ScreenStatus> = uiEvent.receiveAsFlow()

    private val mutex = Mutex()

    fun asyncReduce(action: (UIState) -> UIState) {
        viewModelScope.launch {
            mutex.withLock {
                uiState.emit(action(currentState))
            }
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
                buttonClickAction.onError()
            }
        }
    }

    abstract override fun validate(): Boolean
}