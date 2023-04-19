package com.example.duck.fastnotes.features.login.navigation

import kotlinx.coroutines.flow.StateFlow

interface ButtonActionsProducer {
    val buttonState: StateFlow<StartedButtonState>

    fun onButtonClick()

    fun onButtonAnimationEnd()
}

data class StartedButtonState(
    val textState: ButtonTextState,
    val enabled: Boolean
)