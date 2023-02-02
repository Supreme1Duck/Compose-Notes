package com.example.duck.fastnotes.features.login.button

import kotlinx.coroutines.flow.Flow

interface ButtonClickReceiver {

    val clicked: Flow<Unit>

    fun onError()
}