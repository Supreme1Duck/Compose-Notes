package com.example.duck.fastnotes.utils.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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


// Base dialog to use in app
@Preview
@Composable
fun BaseDialog(
    title: String = "",
    state: DialogState.Error = DialogState.Error(""),
    onCloseDialog: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        icon = {
            Image(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
                painter = painterResource(id = R.drawable.dead),
                contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                text = state.error,
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