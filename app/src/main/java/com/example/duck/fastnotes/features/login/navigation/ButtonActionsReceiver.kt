package com.example.duck.fastnotes.features.login.navigation

import kotlinx.coroutines.flow.Flow

interface ButtonActionsReceiver {

    val clickActionFlow: Flow<Unit>

    fun onScreenSuccess()
}