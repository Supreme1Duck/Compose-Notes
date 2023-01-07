package com.example.duck.fastnotes.features.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.WelcomeScreenTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
fun WelcomeScreenWrapper(welcomeViewModel: WelcomeViewModel = viewModel()) {
    val navController = rememberAnimatedNavController()

    val state by welcomeViewModel.state.collectAsState()

    val onAgreementChange = remember { { it: Boolean -> } }

    LaunchedEffect(key1 = state) {
        when (state.navigateAction) {
            NavigateActions.ACTION_TO_SIGN_UP -> {
                navController.navigate(WelcomeScreenRoutes.SIGN_UP_SCREEN)
            }
            NavigateActions.ACTION_SIGN_UP -> {

            }
            NavigateActions.ACTION_TO_SIGN_IN -> {
//                navController.navigate(WelcomeScreenRoutes.SIGN_IN_SCREEN)
            }
            NavigateActions.ACTION_SIGN_IN -> {

            }
            NavigateActions.ACTION_TO_CONTINUE_WITHOUT_REGISTRATION -> {

            }
            null -> {
                Log.d("DDebug", "No Navigation")
            }
        }
    }

    val onContinueWithoutRegistration = {
        Log.d("DDebug", "On Continue without registration")
    }

    Column(Modifier.fillMaxSize()) {
        WelcomeNavHost(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            navController = navController,
            onAgreementChange = onAgreementChange
        )

        StartedButton(
            modifier = Modifier.fillMaxWidth(),
            state.buttonState,
        ) {
            welcomeViewModel.sendNavigateIntent(navController.currentBackStackEntry?.destination?.route ?: "")
        }
    }
}
