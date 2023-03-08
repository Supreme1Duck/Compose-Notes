package com.example.duck.fastnotes.features.login.signup

import com.example.duck.fastnotes.features.login.LoginUtils.isValidEmail
import com.example.duck.fastnotes.features.login.LoginUtils.isValidPassword
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel

class SignUpViewModel: WelcomeBaseViewModel<SignUpState>(SignUpState.initialState()) {

    fun onEmailChanged(email: String) {
        reduce { state ->
            state.copy(email = email, emailError = false)
        }
    }

    fun onPasswordChanged(password: String) {
        reduce { state ->
            state.copy(password = password, passwordError = false)
        }
    }

    override fun validate(): Boolean {
        if (!currentState.email.isValidEmail()) {
            reduce { it.copy(emailError = true) }
            return false
        }
        if (!currentState.password.isValidPassword()) {
            reduce { it.copy(passwordError = true) }
            return false
        }
        return true
    }
}

data class SignUpState(
    val email: String,
    val password: String,
    val emailError: Boolean,
    val passwordError: Boolean,
) {
    companion object {
        fun initialState(): SignUpState {
            return SignUpState("", "", emailError = false, passwordError = false)
        }
    }
}