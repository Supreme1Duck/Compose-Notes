package com.example.duck.fastnotes.features.login.button

import kotlinx.coroutines.flow.Flow

interface ButtonClickAction {

    fun onClick()

    val onError: Flow<Unit>
}