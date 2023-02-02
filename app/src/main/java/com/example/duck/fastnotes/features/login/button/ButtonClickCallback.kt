package com.example.duck.fastnotes.features.login.button

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ButtonClickCallback: ButtonClickReceiver, ButtonClickAction {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val _clicked = Channel<Unit>()
    override val clicked = _clicked.receiveAsFlow()

    private val _onError = Channel<Unit>()
    override val onError = _onError.receiveAsFlow()

    override fun onError() {
        coroutineScope.launch {
            _onError.send(Unit)
        }
    }

    override fun onClick() {
        coroutineScope.launch {
            _clicked.send(Unit)
        }
    }
}