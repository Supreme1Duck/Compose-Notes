package com.example.duck.fastnotes.features.login.signup

import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.PasswordValidationResult
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.isValidEmail
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.isValidPassword
import com.example.duck.fastnotes.utils.ui.ObserverUtils.call
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
): WelcomeBaseViewModel<SignUpState>(SignUpState.initialState()) {

    private val _continueWithoutRegistrationAction = Channel<Unit>()
    val continueWithoutRegistrationAction = _continueWithoutRegistrationAction.consumeAsFlow()

    fun onEmailChanged(email: String) {
        reduce { state ->
            state.copy(email = email, emailError = false)
        }
    }

    fun onPasswordChanged(password: String) {
        reduce { state ->
            state.copy(password = password, passwordValidationResult = PasswordValidationResult.Success)
        }
    }

    override suspend fun validate(): Boolean {

        val (email, password) = currentState

        if (!email.isValidEmail()) {
            reduce { it.copy(emailError = true) }
            return false
        }

        val passwordValidationResult = validatePassword(password)
        if (passwordValidationResult !is PasswordValidationResult.Success) {
            reduce { it.copy(passwordValidationResult = passwordValidationResult) }
            return false
        }

        return signUpWithEmail(email, password)
    }

    private fun validatePassword(password: String): PasswordValidationResult {
        return password.isValidPassword()
    }

    private suspend fun signUpWithEmail(email: String, password: String): Boolean {
        userInfoRepository.registerUser(email, password)
        return true
    }

    fun onContinueWithoutRegistration() {
        viewModelScope.launch {
            userInfoRepository.setContinuedWithoutRegistration()
            _continueWithoutRegistrationAction.call()
        }
    }
}

data class SignUpState(
    val email: String,
    val password: String,
    val emailError: Boolean,
    val passwordValidationResult: PasswordValidationResult
) {
    companion object {
        fun initialState(): SignUpState {
            return SignUpState(
                email = "",
                password = "",
                emailError = false,
                passwordValidationResult = PasswordValidationResult.Success
            )
        }
    }
}