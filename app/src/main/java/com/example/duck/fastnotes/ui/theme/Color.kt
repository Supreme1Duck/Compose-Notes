package com.example.duck.fastnotes.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFFFFFFFF)
val PrimaryVariantColor = Color(0xFF003D33)
val SecondaryColor = Color(0xFFAAAAAA)
val SecondaryDarkerColor = Color(0xFF575757)

val OnPrimaryColor = Color.Black
val OnSecondaryColor = Color(0xFF8900FF)

val BackgroundColor = Color(0xFFFAFAFA)

val BlackColor = Color(0xFF101010)

val PersonalNoteColor = Color(0xFFFF4F4A)
val WorkNoteColor = Color(0xFF80C8FF)
val HealthNoteColor = Color(0xFFFFF44A)
val RelaxNoteColor = Color(0xFF4AFF4D)
val EducationNoteColor = Color(0xFFD84AFF)
val SportNoteColor = Color(0xFFFF924A)
val ShoppingNoteColor = Color(0xFFFF4AA8)
val DefaultNoteColor = Color(0xFF9E9D9D)


val WelcomePrimaryColor = Color(0xFFFFFFFF)
val WelcomePrimaryVariantColor = Color(0xFFFFFFFF)
val WelcomeOnPrimaryColor = Color(0xFF101010)
val WelcomeSecondaryColor = Color(0xFFEAEAEA)
val WelcomeSecondaryVariantColor = Color(0xFF1B1A17)
val WelcomeOnSecondaryColor = Color.Black
val WelcomeTertiaryColor = Color(0xE4E31CFA)

val BasePurple = Color(0xE47F09D5)

val ErrorColor = Color(0xFFEF4444)
val WelcomeBackgroundColor = Color.White

@Immutable
data class WelcomeScreenColors(
    val primary: Color = WelcomePrimaryColor,
    val primaryVariant : Color = WelcomePrimaryVariantColor,
    val onPrimary: Color = WelcomeOnPrimaryColor,
    val secondary: Color = WelcomeSecondaryColor,
    val secondaryVariant: Color = WelcomeSecondaryVariantColor,
    val onSecondary: Color = WelcomeOnSecondaryColor,
    val tertiary: Color = WelcomeTertiaryColor,
    val background: Color = WelcomeBackgroundColor,
    val error: Color = ErrorColor
)

val WelcomeScreenColorsComposition = staticCompositionLocalOf {
    WelcomeScreenColors()
}

@Immutable
data class MainColors(
    val primary: Color = WelcomePrimaryColor,
    val primaryVariant : Color = WelcomePrimaryVariantColor,
    val onPrimary: Color = WelcomeOnPrimaryColor,
    val secondary: Color = WelcomeSecondaryColor,
    val secondaryVariant: Color = WelcomeSecondaryVariantColor,
    val onSecondary: Color = WelcomeOnSecondaryColor,
    val tertiary: Color = WelcomeTertiaryColor,
    val background: Color = WelcomeBackgroundColor,
    val error: Color = ErrorColor
)

val MainColorsComposition = staticCompositionLocalOf {
    MainColors()
}