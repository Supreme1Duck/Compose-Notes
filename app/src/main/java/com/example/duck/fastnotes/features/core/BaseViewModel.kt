package com.example.duck.fastnotes.features.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<UIState>(initialUIState: UIState): ViewModel() {

    val uiState: UIState
        get() = _uiStateFlow.value

    private val _uiStateFlow = MutableStateFlow(initialUIState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorDialogEvent = Channel<ErrorDialogEvent>()
    val errorDialogEvent = _errorDialogEvent.receiveAsFlow()

    private val _showLoading = Channel<Boolean>()
    val showLoading = _showLoading.receiveAsFlow()

    fun reduce(action: (UIState) -> UIState) {
        _uiStateFlow.update { action(uiState) }
    }

    fun onCloseDialog() {
        viewModelScope.launch {
            _errorDialogEvent.send(ErrorDialogEvent.None)
        }
    }

    fun showLoading() {
        viewModelScope.launch {
            _showLoading.send(true)
        }
    }

    fun stopLoading() {
        viewModelScope.launch {
            _showLoading.send(false)
        }
    }
}