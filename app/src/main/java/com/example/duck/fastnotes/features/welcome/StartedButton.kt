package com.example.duck.fastnotes.features.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.ViewUtils.roundRectShadow
import com.example.duck.fastnotes.utils.ui.CustomShadowParams

@Composable
fun StartedButton(modifier: Modifier, state: ButtonState, onClick: () -> Unit) {
    var textVisibility by mutableStateOf(true)
    var checkMarkVisibility by mutableStateOf(false)

    val enabled = state.enabled

    val text = state.text

    Button(
        modifier = modifier
            .padding(horizontal = 100.dp)
            .padding(bottom = 20.dp)
            .height(45.dp)
            .roundRectShadow(
                CustomShadowParams.defaultButtonShadow(),
                13.dp
            )
            .fillMaxWidth(),
        onClick = {
            onClick()
            textVisibility = false
            checkMarkVisibility = true
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
        AnimatedVisibility(visible = textVisibility,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally(animationSpec = tween()) + fadeOut(animationSpec = tween())
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                style = WelcomeTheme.typography.button,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )
        }

        AnimatedVisibility(visible = !textVisibility && checkMarkVisibility,
            enter = slideInHorizontally(animationSpec = tween(700)) + fadeIn()
        ) {
            Icon(
                Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.welcome_screen_checkmark_description),
                modifier = Modifier.wrapContentSize(),
                tint = Color.White
            )
        }
    }
}

open class ButtonState(val enabled: Boolean, val text: String)