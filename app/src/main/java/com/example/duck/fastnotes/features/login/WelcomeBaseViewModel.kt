package com.example.duck.fastnotes.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.utils.ui.DialogState
import com.google.firebase.FirebaseException
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

    private val _errorEvent = Channel<LoginError>()

    private val _showDialogEvent = Channel<DialogState>()
    val showDialogEvent = _showDialogEvent.receiveAsFlow()

    private val _buttonClickable = MutableSharedFlow<Boolean>()
    val buttonClickable = _buttonClickable.asSharedFlow()

    init {
        viewModelScope.launch {
            _errorEvent.receiveAsFlow().collect {
                when (it) {
                    is LoginError.FirebaseError -> {
                        _showDialogEvent.send(DialogState.Error(it.error))
                    }
                    LoginError.UnknownError -> {
                        _showDialogEvent.send(DialogState.UnknownError)
                    }
                }
            }
        }
    }

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
            setButtonClickable(false)
            try {
                val result = validate()

                if (result) {
                    sendEvent(ScreenStatus.Success)
                } else {
                    sendEvent(ScreenStatus.Failure)
                }
            } catch (e: FirebaseException) {
                _errorEvent.send(LoginError.FirebaseError(e.localizedMessage ?: ""))
            } catch (e: Exception) {
                _errorEvent.send(LoginError.UnknownError)
            }
        }.invokeOnCompletion {
            setButtonClickable(true)
        }
    }

    fun setButtonClickable(clickable: Boolean) {
        viewModelScope.launch {
            _buttonClickable.emit(clickable)
        }
    }

    fun onCloseDialog() {
        viewModelScope.launch {
            _showDialogEvent.send(DialogState.None)
        }
    }

    abstract override suspend fun validate(): Boolean
}