package com.example.duck.fastnotes.utils.ui

sealed interface FirebaseResult {
    object Success: FirebaseResult
    class FirebaseError(val error: String): FirebaseResult
    object UnknownError: FirebaseResult
}