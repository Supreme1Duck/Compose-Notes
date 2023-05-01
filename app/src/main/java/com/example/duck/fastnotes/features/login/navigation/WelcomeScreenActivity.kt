@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.duck.fastnotes.features.login.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.features.dashboard.DashboardActivity
import com.example.duck.fastnotes.features.login.button.StartedButton
import com.example.duck.fastnotes.ui.theme.WelcomeScreenTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreenWrapper(welcomeNavigationViewModel: WelcomeNavigationViewModel = viewModel()) {
    val navController = rememberAnimatedNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val buttonState by welcomeNavigationViewModel.buttonState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        welcomeNavigationViewModel.event
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect {
                when (it) {
                    NavigateActions.ACTION_TO_SIGN_UP -> {
                        navController.navigate(WelcomeScreenRoutes.SIGN_UP_SCREEN)
                    }
                    NavigateActions.ACTION_SIGN_UP -> {
                        DashboardActivity.create(context)
                    }
                    NavigateActions.ACTION_TO_SIGN_IN -> {
                        navController.navigate(WelcomeScreenRoutes.SIGN_IN_SCREEN)
                    }
                    NavigateActions.ACTION_SIGN_IN -> {
                        DashboardActivity.create(context)
                    }
                    NavigateActions.ACTION_TO_CONTINUE_WITHOUT_REGISTRATION -> {
                        DashboardActivity.create(context)
                    }
                }
            }
    }

    LaunchedEffect(key1 = Unit) {
        navController.currentBackStackEntryFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect {
                welcomeNavigationViewModel.sendNavigateIntent(it.destination.route ?: "")
            }
    }

    Column(Modifier.fillMaxSize()) {
        WelcomeNavHost(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            navController = navController,
            buttonActionsReceiver = welcomeNavigationViewModel,
            onSignIn = welcomeNavigationViewModel::onSignIn,
            onContinueWithoutRegistration = welcomeNavigationViewModel::onContinueWithoutRegistration
        )

        StartedButton(
            modifier = Modifier.fillMaxWidth(),
            state = buttonState,
            viewModel = welcomeNavigationViewModel
        )
    }
}