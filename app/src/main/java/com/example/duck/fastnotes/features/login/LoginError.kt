package com.example.duck.fastnotes.features.login

sealed interface LoginError {
    class FirebaseError(val error: String): LoginError
    object UnknownError: LoginError
}