package com.example.duck.fastnotes.features.login.welcome

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.ScreenStatus
import com.example.duck.fastnotes.features.login.navigation.ButtonActionsReceiver
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel = hiltViewModel(),
    buttonActionsReceiver: ButtonActionsReceiver,
    isOnTop: Boolean = false
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifeCycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = isOnTop) {
        if (isOnTop) {
            buttonActionsReceiver.clickActionFlow.collect {
                viewModel.onButtonClick()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        lifeCycleOwner.lifecycleScope.launch {
            viewModel.event.flowWithLifecycle(lifeCycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        ScreenStatus.Failure -> {
                            Toast.makeText(context, context.getString(R.string.welcome_screen_checkbox_alert), Toast.LENGTH_SHORT).show()
                        }

                        ScreenStatus.Success -> {
                            buttonActionsReceiver.onScreenSuccess()
                        }
                    }
                }
        }
    }

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
            modifier = Modifier
                .height(85.dp)
                .constrainAs(imageSign) {
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
            isChecked = state.isAgreementChecked,
            onCheckedChange = viewModel::setCheckedState
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