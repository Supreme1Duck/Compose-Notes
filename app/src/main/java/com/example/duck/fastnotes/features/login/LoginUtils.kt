package com.example.duck.fastnotes.features.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

object LoginUtils {
    @Composable
    fun NavController.isOnTop(route: String): Boolean {
        return currentBackStackEntryAsState().value?.destination?.route == route
    }
}