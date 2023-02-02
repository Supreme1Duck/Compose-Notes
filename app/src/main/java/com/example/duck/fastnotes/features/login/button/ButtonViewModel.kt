package com.example.duck.fastnotes.features.login.button

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.features.login.navigation.WelcomeNavigationViewModel.Companion.buttonClickCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ButtonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ButtonUIState(clickable = true))
    val uiState = _uiState.asStateFlow()

    private val clickCallback: ButtonClickAction = buttonClickCallback

    init {
        clickCallback.onError
            .onEach {
                _uiState.emit(_uiState.value.copy(clickable = true))
            }
            .launchIn(viewModelScope)
    }

    fun onButtonClick() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(clickable = false))
            clickCallback.onClick()
        }
    }

    fun onAnimationEnd() {
        viewModelScope.launch {
            _uiState.emit(_uiState.value.copy(clickable = true))
        }
    }
}

data class ButtonUIState(
    val clickable: Boolean
)