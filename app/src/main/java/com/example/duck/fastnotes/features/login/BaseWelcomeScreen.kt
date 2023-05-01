package com.example.duck.fastnotes.features.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duck.fastnotes.features.login.navigation.ButtonActionsReceiver
import com.example.duck.fastnotes.utils.ui.DialogState
import com.example.duck.fastnotes.utils.ui.DialogState.Companion.isDialogShown
import com.example.duck.fastnotes.utils.ui.ServerErrorDialog
import kotlinx.coroutines.launch

@Composable
inline fun <reified V: WelcomeBaseViewModel<out Any>> BaseWelcomeScreen(isOnTop: Boolean, buttonActionsReceiver: ButtonActionsReceiver, content: @Composable () -> Unit) {

    val viewModel = hiltViewModel<V>()

    LaunchedEffect(key1 = isOnTop) {
        if (isOnTop) {
            buttonActionsReceiver.clickActionFlow.collect {
                viewModel.onButtonClick()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.event.collect {
                if (it == ScreenStatus.Success) {
                    buttonActionsReceiver.onScreenSuccess()
                }
            }
        }
        launch {
            viewModel.buttonClickable.collect {
                if (it)
                    buttonActionsReceiver.onButtonEnable()
                else buttonActionsReceiver.onButtonDisable()
            }
        }
    }

    val serverDialogState by viewModel.showDialogEvent.collectAsState(initial = DialogState.None)

    if (serverDialogState.isDialogShown()) {
        ServerErrorDialog(
            state = serverDialogState,
            onCloseDialog = viewModel::onCloseDialog
        )
    }

    content()
}