package com.example.duck.fastnotes.features.login.signin

import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel

class SignInViewModel(private val userInfoRepository: UserInfoRepository): WelcomeBaseViewModel<UiState>(UiState.initial()) {

    fun onEmailChanged(email: String) {
        reduce {
            it.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) {
        reduce {
            it.copy(password = password)
        }
    }

    override suspend fun validate(): Boolean {
        val (email, password) = currentState
        userInfoRepository.loginUser(email, password)
        return true
    }
}

data class UiState(
    val email: String,
    val password: String,
    val isButtonEnabled: Boolean = true
) {
    companion object {
        fun initial() = UiState("", "")
    }
}