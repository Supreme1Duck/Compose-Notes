@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.duck.fastnotes.features.login.signup

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.navigation.ButtonActionsReceiver
import com.example.duck.fastnotes.features.login.welcome.ImageLogo
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.PasswordValidationResult
import com.example.duck.fastnotes.utils.ui.DialogState
import com.example.duck.fastnotes.utils.ui.ObserverUtils.collectAsState
import com.example.duck.fastnotes.utils.ui.ServerErrorDialog
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.consumeAsFlow

@Composable
fun SignUpScreen(
    buttonActionsReceiver: ButtonActionsReceiver,
    onContinueWithoutRegistration: () -> Unit = {},
    onSignIn: () -> Unit = {},
    isOnTop: Boolean = false
) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    val uiState by viewModel.state.collectAsState()

    val showDialog by viewModel.showDialogEvent.collectAsState(initial = DialogState.initial)

    LaunchedEffect(key1 = isOnTop) {
        if (isOnTop) {
            buttonActionsReceiver.clickActionFlow.collect {
                viewModel.onButtonClick()
            }
        }
    }

    LaunchedEffect(key1 = uiState.isButtonEnabled) {
        if (uiState.isButtonEnabled) {
            Log.d("DDebug", "on Button Enable")
            buttonActionsReceiver.onButtonEnable()
        } else {
            Log.d("DDebug", "on Button disable")
            buttonActionsReceiver.onButtonDisable()
        }
    }

    if (showDialog.show) {
        Log.d("DDebug", "Show dialog on UI")
        ServerErrorDialog(
            description = showDialog.description,
            onCloseDialog = viewModel::onCloseDialog
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = WelcomeTheme.spacing.default),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MainContent(
            email = uiState.email,
            password = uiState.password,
            isEmailError = uiState.emailError,
            passwordValidationResult = uiState.passwordValidationResult,
            onEmailChange = viewModel::onEmailChanged,
            onPasswordChange = viewModel::onPasswordChanged
        )

        AdditionalMethods(onContinueWithoutRegistration, onSignIn)
    }
}

@Composable
fun MainContent(email: String, password: String, isEmailError: Boolean, passwordValidationResult: PasswordValidationResult, onEmailChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogo(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = WelcomeTheme.spacing.small)
                .height(70.dp)
                .width(120.dp)
        )

        SignUpLogo(modifier = Modifier.padding(top = WelcomeTheme.spacing.larger))

        SignUpTitle()

        EmailInput(modifier = Modifier.padding(top = WelcomeTheme.spacing.extraLarger), email, isEmailError) {
            onEmailChange(it)
        }

        PasswordInput(modifier = Modifier.padding(top = WelcomeTheme.spacing.default), password, passwordValidationResult) {
            onPasswordChange(it)
        }
    }
}

@Composable
fun AdditionalMethods(onContinueWithoutRegistration: () -> Unit, onSignIn: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = WelcomeTheme.spacing.default, bottom = WelcomeTheme.spacing.default)
    ) {
        ContinueWithoutRegText(onContinueWithoutRegistration = onContinueWithoutRegistration)

        SignInText(onSignIn = onSignIn)
    }
}

@Composable
fun SignUpLogo(modifier: Modifier) {
    Icon(
        modifier = modifier.size(140.dp),
        painter = painterResource(id = R.mipmap.ic_sign_up_foreground),
        contentDescription = null
    )
}

@Composable
fun SignUpTitle() {
    Text(
        text = stringResource(id = R.string.sign_up_screen_title),
        style = WelcomeTheme.typography.h3.copy(
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun EmailInput(modifier: Modifier, text: String, isError: Boolean, onValueChange: (String) -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            label = { Text(text = LocalContext.current.getString(R.string.sign_up_screen_email_hint)) },
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            isError = isError,
            placeholder = {
                Text(text = stringResource(id = R.string.sign_up_screen_email_hint))              
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = null)
            }
        )
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    text: String,
    validationStatus: PasswordValidationResult,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            label = { Text(text = context.getString(R.string.sign_up_screen_password_hint)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.sign_up_screen_password_hint),
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
            },
            supportingText = {
                when (validationStatus) {
                    PasswordValidationResult.PasswordValidationError.SpecialSymbolsError -> {
                        Text(
                            text = context.getString(R.string.sign_up_screen_password_special_symbols_not_allowed),
                            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.error)
                        )
                    }
                    PasswordValidationResult.PasswordValidationError.SpaceError -> {
                        Text(
                            text = context.getString(R.string.sign_up_screen_password_spaces_not_allowed),
                            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.error)
                        )
                    }
                    PasswordValidationResult.PasswordValidationError.Less10 -> {
                        Text(
                            text = context.getString(R.string.sign_up_screen_password_min_10_characters),
                            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.error)
                        )
                    }
                    PasswordValidationResult.Success -> {}
                }
            }
        )
    }
}

@Composable
fun ContinueWithoutRegText(modifier: Modifier = Modifier, onContinueWithoutRegistration: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.noRippleClickable { onContinueWithoutRegistration() }) {
        Divider(
            modifier = Modifier.weight(1f),
            color = WelcomeTheme.colors.onPrimary,
            thickness = 1.dp
        )
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(id = R.string.sign_up_screen_continue_without_reg),
            textAlign = TextAlign.Center,
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.onPrimary, fontSize = 20.sp)
        )
        Divider(
            modifier = Modifier.weight(1f),
            color = WelcomeTheme.colors.onPrimary,
            thickness = 1.dp
        )
    }
}

@Composable
fun SignInText(modifier: Modifier = Modifier, onSignIn: () -> Unit) {
    Row(modifier) {
        Text(
            text = stringResource(id = R.string.sign_up_already_have_acc),
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.onPrimary)
        )

        Text(
            modifier = Modifier
                .padding(start = WelcomeTheme.spacing.smaller)
                .noRippleClickable {
                    onSignIn()
                },
            text = stringResource(id = R.string.sign_up_screen_action_sign_in),
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.tertiary),
            textDecoration = TextDecoration.Underline
        )
    }
}