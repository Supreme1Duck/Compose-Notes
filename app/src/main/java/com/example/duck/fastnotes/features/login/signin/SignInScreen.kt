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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.signup.EmailInput
import com.example.duck.fastnotes.features.login.signup.MainContent
import com.example.duck.fastnotes.features.login.signup.PasswordInput
import com.example.duck.fastnotes.features.login.signup.ValidationUtils
import com.example.duck.fastnotes.ui.theme.WelcomeTheme

@Composable
fun SignInScreen() {
    val viewModel = hiltViewModel<SignInViewModel>()

    val uiState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(WelcomeTheme.spacing.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MainContent(title = stringResource(id = R.string.sign_in_screen_action))

        SignInInputs(
            uiState.email,
            uiState.password,
            viewModel::onEmailChanged,
            viewModel::onPasswordChanged
        )
    }
}

@Composable
fun SignInInputs(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    EmailInput(modifier = Modifier,
        text = email,
        isError = false,
        onValueChange = onEmailChanged
    )

    PasswordInput(modifier = Modifier,
        text = password,
        validationStatus = ValidationUtils.PasswordValidationResult.Success,
        onValueChange = onPasswordChanged
    )
}