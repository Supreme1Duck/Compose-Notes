package com.example.duck.fastnotes.features.login

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ButtonClickCallback {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val _clicked = Channel<Unit>(1)
    val clicked = _clicked.receiveAsFlow()

    fun onClick() {
        coroutineScope.launch {
            _clicked.send(Unit)
        }
    }
}