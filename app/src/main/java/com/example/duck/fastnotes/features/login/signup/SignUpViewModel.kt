package com.example.duck.fastnotes.features.login.signup

import com.example.duck.fastnotes.features.login.LoginUtils.isValidEmail
import com.example.duck.fastnotes.features.login.LoginUtils.isValidPassword
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel

class SignUpViewModel: WelcomeBaseViewModel<SignUpState>(SignUpState.initialState()) {

    fun onEmailChanged(email: String) {
        if (email.isValidEmail())
            asyncReduce { state ->
                state.copy(email = email)
            }
    }

    fun onPasswordChanged(password: String) {
        if (password.isValidPassword())
            asyncReduce { state ->
                state.copy(password = password)
            }
    }

    override fun validate(): Boolean {
        return currentState.email.isValidEmail() && currentState.password.isValidPassword()
    }
}

data class SignUpState(
    val email: String,
    val password: String
) {
    companion object {
        fun initialState(): SignUpState {
            return SignUpState("", "")
        }
    }
}