package com.example.duck.fastnotes.features.login.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.navigation.ButtonActionsReceiver
import com.example.duck.fastnotes.ui.theme.WelcomeTheme

@Composable
fun SignInScreen(
    buttonActionsReceiver: ButtonActionsReceiver,
    isOnTop: Boolean = false
) {

    Column(Modifier
        .fillMaxSize()
        .padding(WelcomeTheme.spacing.large), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = stringResource(id = R.string.sign_in_screen_action),
            style = WelcomeTheme.typography.h1.copy(
                fontWeight = FontWeight.Normal,
            )
        )

    }
}