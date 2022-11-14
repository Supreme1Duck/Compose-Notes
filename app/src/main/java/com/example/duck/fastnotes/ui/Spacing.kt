package com.example.duck.fastnotes.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraSmall: Dp = 4.dp,
    val smaller: Dp = 6.dp,
    val small: Dp = 8.dp,
    val default: Dp = 16.dp,
    val large: Dp = 20.dp,
    val larger: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
    val bottom: Dp = 40.dp,
)

val WelcomeScreenSpacingComposition = staticCompositionLocalOf {
    Spacing()
}