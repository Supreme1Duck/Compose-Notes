package com.example.duck.fastnotes.features.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.WelcomeScreenTheme
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.Dimens
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class WelcomeScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeScreenTheme {
                WelcomeScreenWrapper()
            }
        }
    }

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, WelcomeScreenActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreenWrapper() {
    var navController = rememberAnimatedNavController()
    var nextEnabled by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var buttonState : StartedButtonState by remember {
        mutableStateOf(
            StartedButtonState.WelcomeScreenStateDisabled(context.getString(R.string.welcome_screen_action))
        )
    }

    val onAgreementChange = { it: Boolean ->
        buttonState =
            if (it) StartedButtonState.WelcomeScreenStateEnabled(context.getString(R.string.welcome_screen_action))
            else StartedButtonState.WelcomeScreenStateDisabled(context.getString(R.string.welcome_screen_action))
    }

    Column(Modifier.fillMaxSize()) {
        WelcomeNavHost(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
            navController = navController,
            onAgreementChange = onAgreementChange
        )

        StartedButton(
            modifier = Modifier.fillMaxWidth(),
            buttonState
        ) {
            if (buttonState is StartedButtonState.WelcomeScreenStateEnabled) {
                navController.navigate(WelcomeScreenRoutes.SIGN_IN_SCREEN)
                buttonState = StartedButtonState.SignInState(context.getString(R.string.welcome_screen_sign_up))
            }

            if (buttonState is StartedButtonState.SignInState) {
                Log.d("DDebug", "Signed in")
            }

            if (buttonState is StartedButtonState.SignUpState) {
                Log.d("DDebug", "Signed Up")
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onAgreementConfirmed: (Boolean) -> Unit
) {
    var isAgreementConfirmed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .background(WelcomeTheme.colors.background),
    ) {
        val (title, appName, imageSign, userInfo, userAgreement) = createRefs()

        WelcomeText(text = context.resources.getString(R.string.welcome_screen_hello),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, Dimens.WELCOME_SCREEN_WELCOME_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
        })

        AppName(text = context.resources.getString(R.string.welcome_screen_title),
            modifier = Modifier.constrainAs(appName) {
                top.linkTo(title.top, margin = Dimens.WELCOME_SCREEN_TITLE_MARGIN)
                start.linkTo(title.start)
                end.linkTo(title.end)
            },
        )

        ImageLogo(
            modifier = Modifier.constrainAs(imageSign) {
                top.linkTo(appName.bottom, margin = 80.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        UserInfo(text = stringResource(id = R.string.welcome_screen_terms_info),
            modifier = Modifier.constrainAs(userInfo) {
                bottom.linkTo(userAgreement.top, 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
        })

        UserAgreement(
            modifier = Modifier.constrainAs(userAgreement) {
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            text = stringResource(id = R.string.welcome_screen_terms_checkbox),
            isChecked = isAgreementConfirmed,
            onCheckedChange = {
                onAgreementConfirmed(it)
                isAgreementConfirmed = it
            }
        )
    }
}

@Composable
fun WelcomeText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = WelcomeTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AppName(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.wrapContentWidth(),
        style = WelcomeTheme.typography.h1.copy(
            fontWeight = FontWeight.Normal,
            shadow = Shadow(
                color = WelcomeTheme.colors.onPrimary.copy(alpha = 0.3f),
                offset = Offset(2f, 7f),
                blurRadius = 6f
            )
        ),
        text = text,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(85.dp)
            .rotate(-1f)
    )
}

@Composable
fun UserInfo(modifier: Modifier, text: String) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(horizontal = WelcomeTheme.spacing.default)
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = WelcomeTheme.colors.secondaryVariant
    ) {
        Text(modifier = Modifier.padding(WelcomeTheme.spacing.default),
            textAlign = TextAlign.Center,
            style = WelcomeTheme.typography.caption,
            text = text
        )
    }
}

@Composable
fun UserAgreement(modifier: Modifier, text: String, isChecked: Boolean, onCheckedChange: (isChecked: Boolean) -> Unit) {
    Row(modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(style = WelcomeTheme.typography.subtitle1, text = text)

        Checkbox(
            modifier = Modifier
                .size(48.dp)
                .padding(start = 10.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = WelcomeTheme.colors.secondaryVariant,
                uncheckedColor = WelcomeTheme.colors.secondaryVariant
            )
        )
    }
}

sealed class StartedButtonState(enabled: Boolean, text: String): ButtonState(enabled, text) {
    class WelcomeScreenStateDisabled(text: String) : StartedButtonState(false, text)
    class WelcomeScreenStateEnabled(text: String) : StartedButtonState(true, text)
    class SignInState(text: String) : StartedButtonState(true, text)
    class SignUpState(text: String) : StartedButtonState(true, text)
}
