package com.example.duck.fastnotes.features.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.FastNotesTheme
import com.example.duck.fastnotes.ui.theme.OnPrimaryColor
import com.example.duck.fastnotes.utils.Dimens
import timber.log.Timber

class DashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DashboardScreen()
        }
    }
}

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
                FloatingActionButton(onClick = {
                    Timber.tag("AndrewDebug").d("Navigation to editing")
                }, modifier = Modifier.padding(bottom = 10.dp)) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.dashboard_navigation_edit),
                        tint = OnPrimaryColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }) {
            DashboardNavigation(navController = navController)
        }
    }
}

