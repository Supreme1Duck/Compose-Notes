package com.example.duck.fastnotes.features.login.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.LoginUtils.isOnTop
import com.example.duck.fastnotes.features.login.BaseWelcomeScreen
import com.example.duck.fastnotes.features.login.navigation.WelcomeScreenRoutes.SIGN_IN_SCREEN
import com.example.duck.fastnotes.features.login.navigation.WelcomeScreenRoutes.SIGN_UP_SCREEN
import com.example.duck.fastnotes.features.login.navigation.WelcomeScreenRoutes.WELCOME_SCREEN
import com.example.duck.fastnotes.features.login.signin.SignInScreen
import com.example.duck.fastnotes.features.login.signin.SignInViewModel
import com.example.duck.fastnotes.features.login.signup.SignUpScreen
import com.example.duck.fastnotes.features.login.signup.SignUpViewModel
import com.example.duck.fastnotes.features.login.welcome.WelcomeScreen
import com.example.duck.fastnotes.features.login.welcome.WelcomeScreenViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

object WelcomeScreenRoutes {
    const val WELCOME_SCREEN = "start"
    const val SIGN_IN_SCREEN = "signIn"
    const val SIGN_UP_SCREEN = "signUp"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = stringResource(id = R.string.welcome_screen_navigation_start),
    buttonActionsReceiver: ButtonActionsReceiver,
    onSignIn: () -> Unit,
    onContinueWithoutRegistration: () -> Unit
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(WELCOME_SCREEN) {
            val isOnTop = navController.isOnTop(route = WELCOME_SCREEN)
            BaseWelcomeScreen<WelcomeScreenViewModel>(isOnTop = isOnTop, buttonActionsReceiver = buttonActionsReceiver) {
                WelcomeScreen()
            }
        }

        composable(SIGN_UP_SCREEN) {
            val isOnTop = navController.isOnTop(route = SIGN_UP_SCREEN)
            BaseWelcomeScreen<SignUpViewModel>(isOnTop, buttonActionsReceiver) {
                SignUpScreen(onContinueWithoutRegistration = onContinueWithoutRegistration, onSignIn = onSignIn)
            }
        }

        composable(SIGN_IN_SCREEN) {
            val isOnTop = navController.isOnTop(SIGN_IN_SCREEN)
            BaseWelcomeScreen<SignInViewModel>(isOnTop, buttonActionsReceiver) {
                SignInScreen()
            }
        }
    }
}