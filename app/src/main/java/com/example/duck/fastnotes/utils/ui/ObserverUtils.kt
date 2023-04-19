package com.example.duck.fastnotes.utils.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

object ObserverUtils {

    suspend fun Channel<Unit>.call(){
        send(Unit)
    }

    @Composable
    fun <E> Channel<E>.collectAsState(initial: E): State<E> {
        return consumeAsFlow().collectAsState(initial = initial)
    }
}