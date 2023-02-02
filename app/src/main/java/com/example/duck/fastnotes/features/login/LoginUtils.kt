package com.example.duck.fastnotes.features.login

import android.util.Patterns

object LoginUtils {

    fun CharSequence.isValidEmail(): Boolean {
        return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun CharSequence.isValidPassword(): Boolean {
        return !isNullOrEmpty()
    }
}