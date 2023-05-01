package com.example.duck.fastnotes.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import com.example.duck.fastnotes.utils.ui.ComposeCompatShadowsRenderer
import com.example.duck.fastnotes.utils.ui.CustomShadowParams


object ViewUtils {

    inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

    fun Modifier.roundRectShadow(
        customShadowParams: CustomShadowParams,
        cornerRadius: Dp
    ) = this.then(ShadowDrawer(customShadowParams, cornerRadius))
}

private class ShadowDrawer(
    private val customShadowParams: CustomShadowParams,
    private val cornerRadius: Dp
) : DrawModifier {

    private val composeCompatShadowsRenderer = ComposeCompatShadowsRenderer()

    override fun ContentDrawScope.draw() {
        customShadowParams.layers.forEach {
            composeCompatShadowsRenderer.paintCompatShadow(
                canvas = this,
                outlineCornerRadius = cornerRadius.toPx(),
                shadow = it
            )
        }
        drawContent()
    }
}