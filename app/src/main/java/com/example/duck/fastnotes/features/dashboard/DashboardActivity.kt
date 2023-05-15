package com.example.duck.fastnotes.features.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.login.navigation.WelcomeScreenActivity
import com.example.duck.fastnotes.ui.theme.FastNotesTheme
import com.example.duck.fastnotes.ui.theme.OnPrimaryColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalComposeUiApi
@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DashboardScreen()
        }

        viewModel.notRegisteredEvent.consumeAsFlow().onEach {
            logout()
        }.launchIn(lifecycleScope)
    }

    companion object {
        fun create(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private fun logout() {
        WelcomeScreenActivity.open(this)
    }
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        DashboardScreens.Home,
        DashboardScreens.Explore,
        DashboardScreens.Profile
    )

    FastNotesTheme {
        Scaffold(Modifier.fillMaxSize(),
            bottomBar = {
                DashboardBottomBar(
                    navController = navController,
                    items = bottomNavigationItems
                )
            },
            floatingActionButton = {
                val currentRoute =
                    navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
                when (currentRoute.value?.destination?.route) {
                    HomeScreens.Main.route -> {
                        FloatingActionButton(onClick = {
                            navController.navigate(HomeScreens.Create.route)
                        }, modifier = Modifier.padding(bottom = 10.dp)) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.dashboard_navigation_edit),
                                tint = OnPrimaryColor,
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                }
            }) {
            DashboardNavigation(navController = navController)
        }
    }
}
