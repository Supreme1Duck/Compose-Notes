package com.example.duck.fastnotes.utils.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.duck.fastnotes.R


@Preview
@Composable
fun ServerErrorDialog(
    title: String = "Exception",
    description: String = "The email address is already in use by another account.",
    onCloseDialog: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        confirmButton = {
            Box {
                TextButton(onClick = onCloseDialog) {
                    Text(text = stringResource(id = R.string.confirm_button_text))
                }
            }
        }
    )
}

// Base dialog state to use in app

open class DialogState(
    val show: Boolean,
    val description: String = ""
) {

    companion object {
        val initial = DialogState(false)

        fun show(description: String) = DialogState(true, description)

        fun close() = DialogState(false)
    }
}