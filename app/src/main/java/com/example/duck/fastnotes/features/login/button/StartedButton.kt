package com.example.duck.fastnotes.features.login.button

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.features.login.navigation.ButtonActionsProducer
import com.example.duck.fastnotes.features.login.navigation.ButtonTextState
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.ViewUtils.roundRectShadow
import com.example.duck.fastnotes.utils.ui.CustomShadowParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StartedButton(
    modifier: Modifier,
    viewModel: ButtonActionsProducer
) {
    val uiState by viewModel.buttonState.collectAsState()

    var startOffset by remember { mutableStateOf(0f) }

    Button(
        modifier = modifier
            .padding(horizontal = 100.dp)
            .padding(bottom = 20.dp)
            .height(45.dp)
            .roundRectShadow(CustomShadowParams.defaultButtonShadow(), 13.dp)
            .fillMaxWidth()
            .onGloballyPositioned { startOffset = it.positionInRoot().x },
        onClick = {
            if (uiState.enabled) {
                viewModel.onButtonClick()
            }
        },
        elevation = ButtonDefaults.elevation(
            0.dp
        ),
        enabled = true,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = WelcomeTheme.colors.onPrimary,
            disabledBackgroundColor = WelcomeTheme.colors.secondary
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (startOffset != 0f)
                AnimatedText(uiState.textState, startOffset, viewModel::onButtonAnimationEnd)
        }
    }
}

@Composable
fun AnimatedText(state: ButtonTextState, startOffset: Float, onAnimationEnd: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val text = stringResource(id = state.text)
    val textFrom = if (state !is ButtonTextState.DisplayText) stringResource(id = state.textFrom!!) else null

    var currentText by remember {
        mutableStateOf(if (state is ButtonTextState.DisplayText) text else textFrom!!)
    }

    var textPosition by remember { mutableStateOf(0f) }
    var iconPosition by remember { mutableStateOf(startOffset) }
    var textFade by remember { mutableStateOf(1f) }
    var iconFade by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = state) {
        when (state) {
            is ButtonTextState.DisplayText -> {
                textPosition = 0f
                textFade = 1f
                onAnimationEnd()
            }
            is ButtonTextState.AnimateForward -> {
                animateForward(
                    { textPosition = it },
                    { iconPosition = it },
                    { textFade = it },
                    { iconFade = it },
                    { currentText = text },
                    coroutineScope,
                    startOffset,
                    onAnimationEnd
                )
            }
            is ButtonTextState.AnimateBackwards -> {
                animateBackwards(
                    { textPosition = it },
                    { textFade = it },
                    { currentText = text },
                    coroutineScope,
                    startOffset,
                    onAnimationEnd
                )
            }
        }
    }

    ButtonText(offset = { textPosition }, textFade = { textFade }, text = currentText)

    if (iconFade != 0f)
        IconButton(iconPosition = { iconPosition }, { iconFade })
}

@Composable
fun ButtonText(offset: () -> Float, textFade: () -> Float, text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = textFade()
                translationX = offset()
            },
        text = text,
        style = WelcomeTheme.typography.button,
        color = Color.White,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp
    )
}

@Composable
fun IconButton(iconPosition: () -> Float, iconFade: () -> Float) {
    Icon(
        imageVector = Icons.Filled.Check,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .size(20.dp)
            .graphicsLayer {
                alpha = iconFade()
                translationX = iconPosition()
            },
        tint = Color.White
    )
}

fun animateForward(
    textPosition: (Float) -> Unit,
    iconPosition: (Float) -> Unit,
    textFade: (Float) -> Unit,
    iconFade: (Float) -> Unit,
    onCurrentAnimationEnd: () -> Unit,
    coroutineScope: CoroutineScope,
    startOffset: Float,
    onAnimationEnd: () -> Unit
) {
    // Animate Current Text
    coroutineScope.launch(Dispatchers.Default) {
        launch {
            animate(
                0f,
                -startOffset,
                animationSpec = tween(durationMillis = 500)
            ) { value, _ ->
                textPosition(value)
            }
        }

        launch {
            animate(
                1f,
                0f,
                animationSpec = tween(durationMillis = 500)
            ) { value, _ ->
                textFade(value)
            }
        }
    }

    // Animate Checkmark
    coroutineScope.launch(Dispatchers.Default) {
        launch {
            animate(startOffset, 0f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                iconPosition(value)
            }

            delay(200)

            onCurrentAnimationEnd()

            animate(0f, -startOffset, animationSpec = tween(durationMillis = 500)) { value, _ ->
                iconPosition(value)
            }
        }

        launch {
            animate(0f, 1f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                iconFade(value)
            }

            delay(200)

            animate(1f, 0f, animationSpec = tween(durationMillis = 500)) { value, _ ->
                iconFade(value)
            }
        }
    }

    // Animate Next Text
    coroutineScope.launch(Dispatchers.Default) {
        delay(500)
        launch {
            animate(
                startOffset,
                0f,
                animationSpec = tween(durationMillis = 500, delayMillis = 220)
            ) { value, _ ->
                textPosition(value)
            }
        }

        launch {
            animate(
                0f,
                1f,
                animationSpec = tween(durationMillis = 500, delayMillis = 200)
            ) { value, _ ->
                textFade(value)
            }
            onAnimationEnd()
        }
    }
}

fun animateBackwards(
    textPosition: (Float) -> Unit,
    textFade: (Float) -> Unit,
    onCurrentAnimationEnd: () -> Unit,
    coroutineScope: CoroutineScope,
    startOffset: Float,
    onAnimationEnd: () -> Unit
) {
    // Animate Current Text
    coroutineScope.launch(Dispatchers.Default) {
        launch {
            animate(
                0f,
                startOffset,
                animationSpec = tween(durationMillis = 300)
            ) { value, _ ->
                textPosition(value)
            }
        }

        launch {
            animate(
                1f,
                0f,
                animationSpec = tween(durationMillis = 300)
            ) { value, _ ->
                textFade(value)
            }
            onCurrentAnimationEnd()
        }
    }

    // Animate Next Text
    coroutineScope.launch(Dispatchers.Default) {
        delay(300)
        launch {
            animate(
                -startOffset,
                0f,
                animationSpec = tween(durationMillis = 300)
            ) { value, _ ->
                textPosition(value)
            }
        }

        launch {
            animate(
                0f,
                1f,
                animationSpec = tween(durationMillis = 300)
            ) { value, _ ->
                textFade(value)
            }
            onAnimationEnd()
        }
    }
}