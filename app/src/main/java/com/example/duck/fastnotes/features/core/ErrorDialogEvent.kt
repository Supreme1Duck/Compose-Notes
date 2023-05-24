package com.example.duck.fastnotes.features.core

sealed interface ErrorDialogEvent {

    object None: ErrorDialogEvent

    class ErrorEvent(val error: String?): ErrorDialogEvent
}