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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.ui.theme.MainTheme

@Preview
@Composable
fun PriorityCreateItem(
    text: String = "",
    hasConfirmed: Boolean = false,
    onConfirmCreation: (text: String) -> Unit = {},
    onCancelCreation: () -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {

        Card(
            modifier = Modifier
                .wrapContentHeight()
                .weight(6f),
            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
            border = BorderStroke(0.5.dp, Color.Black)
        ) {

            Text(
                modifier = Modifier.padding(vertical = MainTheme.spacing.small, horizontal = MainTheme.spacing.default),
                text = "Note for design creation. Capture!",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                .weight(1f)
                .padding(),
            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
            colors = CardDefaults.cardColors(if (hasConfirmed) Color.Green else Color.Red),
            border = BorderStroke(0.5.dp, Color.Black)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = if (hasConfirmed) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}