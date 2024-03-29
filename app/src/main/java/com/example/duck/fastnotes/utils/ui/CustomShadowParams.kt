package com.example.duck.fastnotes.utils.ui

import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.example.duck.fastnotes.ui.theme.WelcomeTheme

/**
 * Композитная тень дизайн системы
 *
 * @param name - название тени
 * @param layers - список теней
 */
data class CustomShadowParams(
    val name: String,
    val layers: List<Shadow>
) {
    companion object {

        const val SHADOW_COLOR = Color.BLACK

        @Composable
        fun defaultButtonShadow(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 2",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 1.toPx,
                        radius = 7.toPx,
                        color = WelcomeTheme.colors.tertiary.toArgb(),
                        colorAlpha = 0.77f,
                        linearGradientParams = GradientParams.defaultLinearGradient()
                    )
                )
            )
        }

        fun shadow3(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 3",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 4.toPx,
                        radius = 17.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.27f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 0.65f),
                                GradientPointAndColorMultiplier(0.75f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow4(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 4",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 4.toPx,
                        radius = 8.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.27f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.8f, 0.15f),
                                GradientPointAndColorMultiplier(1f, 0f),
                            )
                        )
                    )
                )
            )
        }

        fun shadow5(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 5",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 7.toPx,
                        radius = 12.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.22f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.84f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        @Composable
        fun shadowStartButton(): CustomShadowParams {
            Log.d("DDebug", "Color Int = ${WelcomeTheme.colors.secondary.toArgb()}")
            return CustomShadowParams(
                name = "Shadow 5",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 7.toPx,
                        radius = 12.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.22f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.84f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow6(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 6",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 3.toPx,
                        radius = 5.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.27f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.85f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 1.toPx,
                        radius = 14.toPx,
                        color = SHADOW_COLOR,
                        colorAlpha = 0.15f,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.85f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun shadow7(): CustomShadowParams {
            return CustomShadowParams(
                name = "Shadow 7",
                listOf(
                    Shadow(
                        dX = 0.toPx,
                        dY = 11.toPx,
                        radius = 15.toPx,
                        colorAlpha = 0.2f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 01f),
                                GradientPointAndColorMultiplier(0.8f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 9.toPx,
                        radius = 35.toPx,
                        colorAlpha = 0.15f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 1f),
                                GradientPointAndColorMultiplier(0.8f, 0.05f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    ),
                    Shadow(
                        dX = 0.toPx,
                        dY = 24.toPx,
                        radius = 38.toPx,
                        colorAlpha = 0.08f,
                        color = SHADOW_COLOR,
                        linearGradientParams = GradientParams(
                            listOf(
                                GradientPointAndColorMultiplier(0f, 0.8f),
                                GradientPointAndColorMultiplier(0.46f, 0.3f),
                                GradientPointAndColorMultiplier(0.75f, 0.1f),
                                GradientPointAndColorMultiplier(1f, 0f)
                            )
                        )
                    )
                )
            )
        }

        fun noShadow(): CustomShadowParams =
            CustomShadowParams(
                name = "NoShadow",
                listOf(
                    Shadow(
                        dX = 0f,
                        dY = 0f,
                        radius = 0f,
                        color = Color.TRANSPARENT,
                        linearGradientParams = GradientParams.defaultLinearGradient(),
                        colorAlpha = 0f,
                    )
                )
            )
    }
}