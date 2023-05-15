package com.example.duck.fastnotes.features.login.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.signup.MainContent
import com.example.duck.fastnotes.features.login.signup.ValidationUtils
import com.example.duck.fastnotes.ui.theme.WelcomeTheme

@Preview
@Composable
fun SignInScreen() {
    val viewModel = hiltViewModel<SignInViewModel>()

    val uiState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = WelcomeTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MainContent(
            title = stringResource(id = R.string.sign_in_screen_title),
            email = uiState.email,
            password = uiState.password,
            isEmailError = uiState.inputsEmptyError,
            isPasswordError = uiState.inputsEmptyError,
            passwordValidationResult = ValidationUtils.PasswordValidationResult.Success,
            onEmailChange = viewModel::onEmailChanged,
            onPasswordChange = viewModel::onPasswordChanged
        )
    }
}