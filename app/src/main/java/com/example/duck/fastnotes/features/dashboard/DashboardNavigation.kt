package com.example.duck.fastnotes.features.dashboard

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.duck.fastnotes.features.create.ui.EditNoteScreen
import com.example.duck.fastnotes.features.dashboard.home.HomeScreen
import com.example.duck.fastnotes.features.dashboard.personal.ProfileScreen
import com.example.duck.fastnotes.features.dashboard.today.TodayScreen
import com.example.duck.fastnotes.ui.theme.OnSecondaryColor
import com.example.duck.fastnotes.ui.theme.SecondaryColor
import com.example.duck.fastnotes.utils.Dimens

sealed class DashboardScreens(val route: String, val label: String, val icon: ImageVector) {
    object Home : DashboardScreens("home", "Today", Icons.Filled.Home)
    object Explore : DashboardScreens("explore", "Explore", Icons.Filled.DateRange)
    object Profile : DashboardScreens("settings", "Profile", Icons.Filled.Person)
}

sealed class HomeScreens(val route: String) {
    object Main : HomeScreens("main")
    object Create : HomeScreens("create")
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun DashboardNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = DashboardScreens.Home.route, route = "SomeRoute") {

        homeGraph(navController = navController)

        composable(DashboardScreens.Explore.route) {
            TodayScreen()
        }

        composable(DashboardScreens.Profile.route) {
            ProfileScreen()
        }
    }
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(startDestination = HomeScreens.Main.route, route = DashboardScreens.Home.route) {

        composable(HomeScreens.Main.route) {
            HomeScreen()
        }

        composable(HomeScreens.Create.route) {
            EditNoteScreen {
                navController.navigateUp()
            }
        }
    }
}

private fun navigateToSingleNote(
        navController: NavHostController,
        id: Int
) {
    navController.navigate("${HomeScreens.Create.route}/$id")
}

@Composable
fun DashboardBottomBar(navController: NavHostController, items: List<DashboardScreens>) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val currentGraph = navController.currentDestination?.parent?.route

    BottomAppBar(modifier = Modifier.height(Dimens.BOTTOM_BAR_SIZE)) {
        items.forEach { screen ->
            BottomNavigationItem(selected = currentRoute == screen.route || currentGraph == screen.route,
                    icon = { Icon(screen.icon, screen.label) },
                    alwaysShowLabel = false,
                    selectedContentColor = OnSecondaryColor,
                    unselectedContentColor = SecondaryColor,
                    label = { Text(screen.label) },
                    interactionSource = MutableInteractionSource(),
                    onClick = {
                        if (currentRoute != screen.route && currentGraph != screen.route) {
                            navController.navigate(screen.route) {
                                navController.graph.findStartDestination().id.let {
                                    popUpTo(it) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    })
        }
    }
}
