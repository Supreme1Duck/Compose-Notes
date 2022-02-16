package com.example.duck.fastnotes.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.duck.fastnotes.ui.theme.BackgroundColor
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.SecondaryDarkerColor

fun Modifier.defaultModifier() =
    composed {
        this
            .fillMaxWidth()
            .padding(horizontal = Dimens.DEFAULT_MARGIN) }

@Composable
fun TextTitleSmall(text: String, padding: Dp) =
    Text(
        text = text,
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
fun TextSecondaryTitle(text: String) =
    Text(text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.DEFAULT_MARGIN),
        style = textSecondaryTitleBlack()
    )

@Composable
fun TextSecondaryMedium(text: String) =
    Text(text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.DEFAULT_MARGIN))


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
fun textCenteredStyle() =
    TextStyle(
        color = Color.Black,
        fontSize = Dimens.TEXT_LARGE,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
    )

@Composable
fun textDefaultDarkerStyleSmall() =
    TextStyle(
        color = SecondaryDarkerColor,
        fontSize = Dimens.TEXT_SMALL,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )

@Composable
fun textDefaultDarkerStyleMedium() =
    TextStyle(
        color = SecondaryDarkerColor,
        fontSize = Dimens.TEXT_DEFAULT,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
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
fun textDefaultDarkerStyleLarger() =
    TextStyle(
        color = BackgroundColor,
        fontSize = Dimens.TEXT_LARGER,
        fontWeight = FontWeight.Bold,
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
fun textDefaultLightStyle() =
    TextStyle(
        color = Color.White,
        fontSize = Dimens.TEXT_SMALL,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )