package com.example.duck.fastnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.ui.Spacing
import com.example.duck.fastnotes.ui.WelcomeScreenSpacingComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColor
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = Color.White,

    background = BackgroundColor,
)

@Composable
fun MainTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        MainColorsComposition provides MainTheme.colors,
        WelcomeScreenTypographyComposition provides WelcomeTheme.typography,
        WelcomeScreenSpacingComposition provides WelcomeTheme.spacing
    ) {
        val controller = rememberSystemUiController()
        controller.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )

        content()
    }
}

@Composable
fun WelcomeScreenTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        WelcomeScreenColorsComposition provides WelcomeTheme.colors,
        WelcomeScreenTypographyComposition provides WelcomeTheme.typography,
        WelcomeScreenSpacingComposition provides WelcomeTheme.spacing
    ) {
        val controller = rememberSystemUiController()
        controller.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )

        content()
    }
}

object WelcomeTheme {

  val colors: WelcomeScreenColors
    @Composable
    @ReadOnlyComposable
    get() = WelcomeScreenColorsComposition.current

  val typography: WelcomeScreenTypography
    @Composable
    @ReadOnlyComposable
    get() = WelcomeScreenTypographyComposition.current

  val spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = WelcomeScreenSpacingComposition.current
}

object MainTheme {

    val colors: MainColors
        @Composable
        @ReadOnlyComposable
        get() = MainColorsComposition.current

    val typography: WelcomeScreenTypography
        @Composable
        @ReadOnlyComposable
        get() = WelcomeScreenTypographyComposition.current

    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = WelcomeScreenSpacingComposition.current
}

@Composable
fun FastNotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(color = Color.Transparent)
        DarkColorPalette
    } else {
        systemUiController.setNavigationBarColor(Color.White)
        systemUiController.setStatusBarColor(BackgroundColor)
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes,
        content = content
    )
}
