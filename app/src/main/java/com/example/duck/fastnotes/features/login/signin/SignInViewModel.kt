package com.example.duck.fastnotes.features.login.signin

import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
): WelcomeBaseViewModel<UiState>(UiState.initial()) {

    fun onEmailChanged(email: String) {
        reduce {
            it.copy(email = email, inputsEmptyError = false)
        }
    }

    fun onPasswordChanged(password: String) {
        reduce {
            it.copy(password = password, inputsEmptyError = false)
        }
    }

    override suspend fun validate(): Boolean {
        val (email, password) = currentState

        if (email.isEmpty() || password.isEmpty()) {
            reduce {
                it.copy(inputsEmptyError = true)
            }

            return false
        }

        userInfoRepository.loginUser(email, password)
        return true
    }
}

data class UiState(
    val email: String,
    val password: String,
    val inputsEmptyError: Boolean
) {
    companion object {
        fun initial() = UiState("", "", false)
    }
}