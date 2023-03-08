package com.example.duck.fastnotes.features.login.navigation

import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
    * VM used in navigation between login screens.

    * clickActionChannel - channel for button click actions.
    * buttonState - state flow of the started button state.
    * currentScreen - string meaning of the current screen route.
    Must be changed every time screen changes.

    * screenList - list of screen routes.
    * buttonTextsMap - text on the start button dependent on the screen

    * state - state of the welcome navigation screen.
    * event - observable holder for one-time events.
*/
class WelcomeNavigationViewModel : ViewModel(), ButtonActionsProducer, ButtonActionsReceiver {

    private val _clickActionChannel = Channel<Unit>()
    override val clickActionFlow: Flow<Unit> = _clickActionChannel.receiveAsFlow()

    private val _buttonState = MutableStateFlow(ButtonUIState(clickable = false))
    override val buttonState: StateFlow<ButtonUIState> = _buttonState.asStateFlow()

    private var currentScreen: String = WelcomeScreenRoutes.WELCOME_SCREEN

    private val screenList = listOf(
        WelcomeScreenRoutes.WELCOME_SCREEN,
        WelcomeScreenRoutes.SIGN_UP_SCREEN,
        WelcomeScreenRoutes.SIGN_IN_SCREEN
    )

    private val buttonTextsMap = mapOf(
        WelcomeScreenRoutes.WELCOME_SCREEN to R.string.welcome_screen_action,
        WelcomeScreenRoutes.SIGN_UP_SCREEN to R.string.sign_up_screen_action,
        WelcomeScreenRoutes.SIGN_IN_SCREEN to R.string.sign_in_screen_action
    )

    private val _state : MutableStateFlow<WelcomeNavigationState> = MutableStateFlow(
        WelcomeNavigationState(StartedButtonState(textState = ButtonTextState.DisplayText(buttonTextsMap[WelcomeScreenRoutes.WELCOME_SCREEN] ?: 0), true))
    )
    val state = _state.asStateFlow()

    private val _event = Channel<NavigateActions>()
    val event = _event.receiveAsFlow()

    private fun onScreenSuccess(currentDestination: String) {
        viewModelScope.launch {
            val navigationAction = when (currentDestination) {
                WelcomeScreenRoutes.WELCOME_SCREEN -> NavigateActions.ACTION_TO_SIGN_UP
                WelcomeScreenRoutes.SIGN_UP_SCREEN -> NavigateActions.ACTION_SIGN_UP
                WelcomeScreenRoutes.SIGN_IN_SCREEN -> NavigateActions.ACTION_SIGN_IN
                else -> null
            }

            _event.send(navigationAction ?: throw RuntimeException())
        }
    }

    fun sendNavigateIntent(route: String) {
        if (currentScreen == route)
            return

        viewModelScope.launch {
            if (screenList.indexOf(route) < screenList.indexOf(currentScreen)) {
                _state.emit(
                    _state.value.copy(
                        buttonState = StartedButtonState(ButtonTextState.AnimateBackwards(buttonTextsMap[route] ?: 0, buttonTextsMap[currentScreen] ?: 0), true)
                    )
                )

                currentScreen = route
            } else {
                if (route == screenList[0])
                    return@launch

                _state.emit(
                    _state.value.copy(
                        buttonState = StartedButtonState(ButtonTextState.AnimateForward(buttonTextsMap[route] ?: 0, buttonTextsMap[currentScreen] ?: 0), true)
                    )
                )

                currentScreen = route
            }
        }
    }

    override fun onButtonClick() {
        viewModelScope.launch {
            _clickActionChannel.send(Unit)
        }
    }

    override fun onScreenSuccess() {
        onScreenSuccess(currentScreen)
    }

    override fun onButtonAnimationEnd() {
        _buttonState.value = _buttonState.value.copy(clickable = true)
    }

    fun onContinueWithoutRegistration() {

    }

    fun onSignIn() {

    }

    fun onSignUp() {

    }
}

data class WelcomeNavigationState(
    val buttonState: StartedButtonState
)

data class StartedButtonState(
    val textState: ButtonTextState,
    val enabled: Boolean
)

sealed class ButtonTextState(@IntegerRes val text: Int, @IntegerRes val textFrom: Int?) {
    class DisplayText(@IntegerRes text: Int) : ButtonTextState(text, null)
    class AnimateForward(@IntegerRes text: Int, @IntegerRes textFrom:  Int) : ButtonTextState(text, textFrom)
    class AnimateBackwards(@IntegerRes text: Int, @IntegerRes textFrom: Int) : ButtonTextState(text, textFrom)
}

enum class NavigateActions {
    ACTION_TO_SIGN_UP, ACTION_SIGN_UP, ACTION_TO_SIGN_IN, ACTION_SIGN_IN, ACTION_TO_CONTINUE_WITHOUT_REGISTRATION,
}