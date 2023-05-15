package com.example.duck.fastnotes.utils.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ServerErrorDialog(
    title: String = "OOPS!",
    state: DialogState = DialogState.UnknownError,
    onCloseDialog: () -> Unit = {},
) {
    when (state) {
        DialogState.UnknownError -> {
            UnknownErrorDialog(title, onCloseDialog)
        }
        is DialogState.Error -> {
            BaseErrorDialog(title, state, onCloseDialog)
        }
        else -> {}
    }
}

// Base dialog state to use in app

sealed interface DialogState {
    class Error(val error: String): DialogState
    object UnknownError: DialogState
    object None: DialogState

    companion object {
        fun DialogState.isDialogShown(): Boolean {
            return this !is None
        }
    }
}