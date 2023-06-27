package com.example.duck.fastnotes.features.create.bottomsheets.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PriorityCreateItem(
    subTask: SubTask = SubTask("", false),
    isToCreate: Boolean = false,
    onConfirmCreation: (SubTask) -> Unit = {},
    modifyExisting: (String) -> Unit = {},
    onCancelCreation: () -> Unit = {},
) {

    var text by remember { mutableStateOf(subTask.title) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(bottom = MainTheme.spacing.default)
    ) {

        Card(
            modifier = Modifier
                .wrapContentHeight()
                .weight(6f),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
            border = BorderStroke(1.5.dp, Color.Blue),
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue
                ),
                onValueChange = { value ->
                    text = value

                    if (!isToCreate) {
                        modifyExisting(value)
                    }
                },
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp), color = Color.Transparent
        )

        Card(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
            enabled = text.isNotEmpty(),
            elevation = CardDefaults.cardElevation(5.dp, disabledElevation = 5.dp),
            colors = CardDefaults.cardColors(if (text.isEmpty()) Color.Gray else if (isToCreate) Color.Blue else Color.Red),
            onClick = {
                if (isToCreate) {
                    onConfirmCreation(subTask.copy(title = text))
                } else {
                    onCancelCreation()
                }
            }
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = if (isToCreate) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}