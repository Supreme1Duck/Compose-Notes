package com.example.duck.fastnotes.features.login

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

object LoginUtils {

    fun CharSequence.isValidEmail(): Boolean {
        return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun CharSequence.isValidPassword(): Boolean {
        return !isNullOrEmpty() && this.length < 15
    }

    @Composable
    fun NavController.isOnTop(route: String): Boolean {
        return currentBackStackEntryAsState().value?.destination?.route == route
    }
}