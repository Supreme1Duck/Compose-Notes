package com.example.duck.fastnotes.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.ui.theme.BackgroundColor
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.SecondaryDarkerColor


@Composable
fun TextTitleDefault(text: String) =
    Text(
        text = text,
        style = textDefaultTitleStyle()
    )

@Composable
fun TextSecondaryTitle(text: String) =
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.DEFAULT_MARGIN)
            .padding(bottom = Dimens.SMALL_MARGIN),
        style = textSecondaryTitleBlack()
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
fun textDefaultSemiBoldStyle() =
    textDefaultStyle().copy(fontWeight = FontWeight.SemiBold)

@Composable
fun textSecondaryTitleStyle() =
    TextStyle(
        SecondaryDarkerColor,
        fontSize = Dimens.TEXT_DEFAULT,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
        letterSpacing = 2.sp
    )

@Composable
fun textDefaultDarkerStyleLarge() =
    TextStyle(
        color = SecondaryDarkerColor,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )

@Composable
fun textSecondaryTitleBlack() =
    TextStyle(
        color = BlackColor,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start
    )

@Composable
fun textDefaultTitleStyle() =
    TextStyle(
        color = Color.White,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
    )

@Composable
fun textTitleLightLarger() =
    TextStyle(
        color = Color.White,
        fontSize = Dimens.TEXT_TITLE,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        letterSpacing = 1.5.sp
    )

@Composable
fun textErrorStyle() =
    TextStyle(
        color = MaterialTheme.colors.error,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )