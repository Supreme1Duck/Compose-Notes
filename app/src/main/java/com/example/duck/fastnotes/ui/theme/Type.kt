package com.example.duck.fastnotes.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.R

val appFontFamily = FontFamily(
    fonts = listOf(
        Font(resId = R.font.oswald_regular, weight = FontWeight.W400),
        Font(resId = R.font.oswald_semibold, weight = FontWeight.W600),
        Font(resId = R.font.oswald_medium, weight = FontWeight.W500),
        Font(resId = R.font.oswald_light, weight = FontWeight.W300),
        Font(resId = R.font.montserrat_regular, weight = FontWeight.W400),
        Font(resId = R.font.montserrat_black, weight = FontWeight.W600),
        Font(resId = R.font.sourcesanspro_regular, weight = FontWeight.W400),
        Font(resId = R.font.sourcesanspro_black, weight = FontWeight.W600)
    )
)

val welcomeFontFamilyMontserrat = FontFamily(
    Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal)
)

val welcomeFontFamilySourceSans = FontFamily(
    Font(resId = R.font.sourcesanspro_regular)
)

val FastNotesTypography = Typography(
    h1 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    h6 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.W300,
        color = BlackColor
    ),
    caption = TextStyle(
        fontFamily = appFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = BlackColor
    ),
    overline = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = BlackColor
    )
)

val WTypography = Typography(
    h1 = TextStyle(
        fontFamily = welcomeFontFamilySourceSans,
        fontWeight = FontWeight.W600,
        fontSize = 46.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = welcomeFontFamilyMontserrat,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = welcomeFontFamilyMontserrat,
        fontSize = 8.sp,
        fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
        fontFamily = welcomeFontFamilySourceSans,
        fontSize = 14.sp,
        color = Color.White,
        fontWeight = FontWeight.W500
    ),
    button = TextStyle(
        fontFamily = welcomeFontFamilySourceSans,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal
    )
)

data class WelcomeScreenTypography(
    val h1: TextStyle = WTypography.h1,
    val subtitle1: TextStyle = WTypography.subtitle1,
    val subtitle2: TextStyle = WTypography.subtitle2,
    val caption: TextStyle = WTypography.caption,
    val button: TextStyle = WTypography.button
)

val WelcomeScreenTypographyComposition = staticCompositionLocalOf {
    WelcomeScreenTypography()
}