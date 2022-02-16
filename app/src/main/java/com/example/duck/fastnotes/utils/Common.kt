package com.example.duck.fastnotes.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

@Composable
fun TextTitleSmall(text: String, padding: Dp) =
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        style = textCenteredStyle()
    )


@Composable
fun TextDescDefault(text: String) =
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.DEFAULT_MARGIN),
        style = textDefaultStyle()
    )


@Composable
fun textDefaultStyle() =
    TextStyle(
        color = Color.Black,
        fontSize = Dimens.TEXT_SMALL,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )

@Composable
fun textCenteredStyle() =
    TextStyle(
        color = Color.Black,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
    )