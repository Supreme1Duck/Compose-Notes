package com.example.duck.fastnotes.features.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens

@Composable
fun NoteTypeItem(name: String, action: () -> Unit) {
    Card(shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier.clickable {
            action()
        }
    ) {
        Text(
            text = name,
            style = FastNotesTypography.subtitle1.copy(
                color = BlackColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(Dimens.SMALL_MARGIN)
        )
    }
}

@Composable
fun ColorTypeItem(color: Color, onClick : () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.background(Color.White)
            .width(100.dp)
            .height(30.dp)
            .clickable {
                onClick()
            }
    ) {
        Box(modifier = Modifier.padding(Dimens.SMALLER_MARGIN).background(color)
            .size(10.dp))
    }
}

