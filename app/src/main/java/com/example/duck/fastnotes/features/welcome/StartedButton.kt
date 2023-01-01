package com.example.duck.fastnotes.features.welcome

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.ViewUtils.roundRectShadow
import com.example.duck.fastnotes.utils.ui.CustomShadowParams
import com.example.duck.fastnotes.utils.ui.toDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StartedButton(modifier: Modifier, state: ButtonState, onClick: () -> Unit) {
    var animationState by remember { mutableStateOf(AnimationState(animate = false, isFirst = true)) }

    var startOffset by remember { mutableStateOf(0f) }
    var clickable by remember { mutableStateOf(true) }

    val onAnimationEnd = {
        clickable = true
    }

    val enabled = state.enabled
    val text = state.text

    val coroutineScope = rememberCoroutineScope()

    Button(
        modifier = modifier
            .padding(horizontal = 100.dp)
            .padding(bottom = 20.dp)
            .height(45.dp)
            .roundRectShadow(
                CustomShadowParams.defaultButtonShadow(), 13.dp
            )
            .fillMaxWidth()
            .onGloballyPositioned { startOffset = it.positionInRoot().x },
        onClick = {
            if (clickable)
                coroutineScope.launch {
                    clickable = false
                    animationState = animationState.copy(animate = true)
                    delay(500)
                    onClick()
                    animationState = animationState.copy(animate = false, isFirst = false)
                }
        },
        elevation = ButtonDefaults.elevation(
            0.dp
        ),
        enabled = enabled,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = WelcomeTheme.colors.onPrimary,
            disabledBackgroundColor = WelcomeTheme.colors.secondary
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (startOffset != 0f)
                AnimatedText(text, animationState, startOffset, onAnimationEnd)
        }
    }
}

data class AnimationState(
    val animate: Boolean,
    val isFirst: Boolean
)

@Composable
fun AnimatedText(text: String, animationState: AnimationState, startOffset: Float, onAnimationEnd: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    var textPosition by remember { mutableStateOf(0f) }
    var iconPosition by remember { mutableStateOf(startOffset) }
    var textFade by remember { mutableStateOf(1f) }
    var iconFade by remember { mutableStateOf(0f) }

    val animate = animationState.animate
    val isFirst = animationState.isFirst

    LaunchedEffect(key1 = animate to text) {
        // Animate hiding next text with fade
        if (!isFirst) {
            coroutineScope.launch {
                launch {
                    animate(startOffset, 0f, animationSpec = tween(durationMillis = 500, delayMillis = 220)) { value, _ ->
                        textPosition = value
                    }
                    onAnimationEnd()
                }

                launch {
                    animate(0f, 1f, animationSpec = tween(durationMillis = 500, delayMillis = 200)) { value, _ ->
                        textFade = value
                    }
                }
            }
        }

        if (animate) {
        // Animate current text translation with fade
            coroutineScope.launch {
                launch {
                    animate(0f, -startOffset, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        textPosition = value
                    }
                }

                launch {
                    animate(1f, 0f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        textFade = value
                    }
                }
            }

            // Animate checkmark translation with fade
            coroutineScope.launch {
                launch {
                    animate(startOffset, 0f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        iconPosition = value
                    }

                    delay(200)

                    animate(0f, -startOffset, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        iconPosition = value
                    }
                }

                launch {
                    animate(0f, 1f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        iconFade = value
                    }

                    delay(200)

                    animate(1f, 0f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                        iconFade = value
                    }
                }
            }
        }
    }


    Text(
        modifier = Modifier
            .alpha(textFade)
            .fillMaxWidth()
            .offset(x = textPosition.toDp),
        text = text,
        style = WelcomeTheme.typography.button,
        color = Color.White,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp
    )

    Icon(
        imageVector = Icons.Filled.Check,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .size(20.dp)
            .alpha(iconFade)
            .offset(x = iconPosition.toDp),
        tint = Color.White
    )
}

open class ButtonState(val enabled: Boolean, val text: String)