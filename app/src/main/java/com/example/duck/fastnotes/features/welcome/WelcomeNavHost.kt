package com.example.duck.fastnotes.features.welcome

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.welcome.WelcomeScreenRoutes.SIGN_IN_SCREEN
import com.example.duck.fastnotes.features.welcome.WelcomeScreenRoutes.SIGN_UP_SCREEN
import com.example.duck.fastnotes.features.welcome.WelcomeScreenRoutes.WELCOME_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

object WelcomeScreenRoutes {
    internal const val WELCOME_SCREEN = "start"
    internal const val SIGN_IN_SCREEN = "signIn"
    internal const val SIGN_UP_SCREEN = "signUp"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = stringResource(id = R.string.welcome_screen_navigation_start),
    onAgreementChange: (Boolean) -> Unit
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(WELCOME_SCREEN) {
            WelcomeScreen(onAgreementChange)
        }

        composable(SIGN_IN_SCREEN) {
            SignInScreen()
        }

        composable(SIGN_UP_SCREEN) {

        }
    }
}