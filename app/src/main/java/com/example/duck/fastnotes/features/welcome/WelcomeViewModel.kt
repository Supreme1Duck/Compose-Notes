package com.example.duck.fastnotes.features.welcome

import androidx.annotation.IntegerRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
class WelcomeViewModel : ViewModel() {

    private val buttonTextsMap = mapOf(
        WelcomeScreenRoutes.WELCOME_SCREEN to R.string.welcome_screen_action,
        WelcomeScreenRoutes.SIGN_UP_SCREEN to R.string.sign_up_screen_action,
        WelcomeScreenRoutes.SIGN_IN_SCREEN to R.string.sign_in_screen_action
    )

    private val destinationButtonTextsMap = mapOf(
        NavigateActions.ACTION_TO_SIGN_UP to R.string.sign_up_screen_action,
        NavigateActions.ACTION_TO_SIGN_IN to R.string.sign_in_screen_action
    )

    private val _state : MutableStateFlow<WelcomeScreenState> = MutableStateFlow(WelcomeScreenState(null, StartedButtonState(textState = ButtonTextState.DisplayText(buttonTextsMap[WelcomeScreenRoutes.WELCOME_SCREEN] ?: 0), true)))
    val state = _state.asStateFlow()

    fun sendNavigateIntent(currentDestination: String) {
        viewModelScope.launch {
            val navigationAction = when (currentDestination) {
                WelcomeScreenRoutes.WELCOME_SCREEN -> NavigateActions.ACTION_TO_SIGN_UP
                WelcomeScreenRoutes.SIGN_UP_SCREEN -> NavigateActions.ACTION_SIGN_UP
                WelcomeScreenRoutes.SIGN_IN_SCREEN -> NavigateActions.ACTION_SIGN_IN
                else -> null
            }

            val buttonText = buttonTextsMap[currentDestination] ?: 0
            val nextButtonText = destinationButtonTextsMap[navigationAction] ?: 0

            _state.emit(
                _state.value.copy(
                    navigateAction = navigationAction,
                    buttonState = StartedButtonState(ButtonTextState.AnimateForward(text = nextButtonText, textFrom = buttonText), true)
                )
            )
        }
    }

    fun sendNavigateIntent(navigationAction: NavigateActions) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(navigateAction = navigationAction))
        }
    }
}

data class WelcomeScreenState(
    val navigateAction: NavigateActions?,
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