package com.example.duck.fastnotes.features.login

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WelcomeScreensVMDelegate<UIState> {
    val event: Flow<ScreenStatus>

    val state: StateFlow<UIState>

    val currentState: UIState

    fun onButtonClick()

    fun validate(): Boolean
}

sealed interface ScreenStatus {
    object Success: ScreenStatus
    object Failure: ScreenStatus
}