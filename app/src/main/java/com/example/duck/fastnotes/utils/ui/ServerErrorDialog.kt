package com.example.duck.fastnotes.utils.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R

@Preview
@Composable
fun ServerErrorDialog(
    title: String = "OOPS!",
    description: String = "The email address is already in use by another account.",
    onCloseDialog: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        icon = {
            Image(
                modifier = Modifier.height(120.dp).width(120.dp),
                painter = painterResource(id = R.drawable.dead),
                contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = description,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        },
        confirmButton = {
            Box {
                TextButton(onClick = onCloseDialog) {
                    Text(
                        text = stringResource(id = R.string.confirm_button_text)
                    )
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