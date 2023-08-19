@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.duck.fastnotes.features.login.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.PasswordValidationResult
import com.example.duck.fastnotes.features.login.welcome.ImageLogo
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onContinueWithoutRegistration: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.continueWithoutRegistrationAction.collect {
                onContinueWithoutRegistration()
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = WelcomeTheme.spacing.default)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MainContent(
            title = stringResource(id = R.string.sign_up_screen_title),
            email = uiState.email,
            password = uiState.password,
            isEmailError = uiState.emailError,
            isPasswordError = false,
            passwordValidationResult = uiState.passwordValidationResult,
            onEmailChange = viewModel::onEmailChanged,
            onPasswordChange = viewModel::onPasswordChanged
        )

        SignUpMethods(
            onContinueWithoutRegistration = viewModel::onContinueWithoutRegistration,
            onSignIn = onSignIn
        )
    }
}

@Composable
fun MainContent(
    title: String, email: String,
    password: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    passwordValidationResult: PasswordValidationResult,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogo(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = WelcomeTheme.spacing.small)
                .height(70.dp)
                .width(120.dp)
        )

        LoginTitle(title)

        LoginLogo(Modifier.padding(top = WelcomeTheme.spacing.default))

        LoginInputs(
            email = email,
            password = password,
            isEmailError = isEmailError,
            isPasswordError = isPasswordError,
            passwordValidationResult = passwordValidationResult,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange
        )
    }
}

@Composable
fun LoginInputs(
    email: String,
    password: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    passwordValidationResult: PasswordValidationResult,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(top = WelcomeTheme.spacing.default)) {
        EmailInput(text = email, isError = isEmailError) {
            onEmailChange(it)
        }

        PasswordInput(
            modifier = Modifier.padding(bottom = WelcomeTheme.spacing.bottom),
            text = password,
            isPasswordError,
            validationStatus = passwordValidationResult
        ) {
            onPasswordChange(it)
        }
    }
}

@Composable
fun SignUpMethods(
    onContinueWithoutRegistration: () -> Unit,
    onSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = WelcomeTheme.spacing.default, bottom = WelcomeTheme.spacing.default)
    ) {
        ContinueWithoutRegText(modifier = Modifier.padding(bottom = WelcomeTheme.spacing.bottom), onContinueWithoutRegistration = onContinueWithoutRegistration)

        SignInText(onSignIn = onSignIn)
    }
}

@Composable
fun LoginLogo(modifier: Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.spyware),
        contentDescription = null,
        modifier = modifier
            .size(200.dp)
    )
}

@Composable
fun LoginTitle(text: String) {
    Text(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        style = WelcomeTheme.typography.h3.copy(
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun EmailInput(modifier: Modifier = Modifier, text: String, isError: Boolean, onValueChange: (String) -> Unit) {
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
    modifier: Modifier = Modifier,
    text: String,
    isPasswordError: Boolean,
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
            isError = isPasswordError,
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