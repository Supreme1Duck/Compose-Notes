package com.example.duck.fastnotes.utils.ui

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Int.toPx: Float
    get() = dpToPx(this).toFloat()

val Float.toPx: Float
    get() = dpToPx(this).toFloat()

val Float.toDp: Dp
    get() = (this / Resources.getSystem().displayMetrics.density).dp

fun dpToPx(dp: Int): Int =
    (dp * Resources.getSystem().displayMetrics.density).toInt()

fun dpToPx(dp: Float): Int =
    (dp * Resources.getSystem().displayMetrics.density).toInt()

fun pxToDp(px: Float): Dp =
    (px / Resources.getSystem().displayMetrics.density).toDp
