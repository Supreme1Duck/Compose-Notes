package com.example.duck.fastnotes.features.login.navigation

import kotlinx.coroutines.flow.StateFlow

interface ButtonActionsProducer {
    val buttonState: StateFlow<ButtonUIState>

    fun onButtonClick()

    fun onButtonAnimationEnd()
}

data class ButtonUIState(
    val clickable: Boolean
)