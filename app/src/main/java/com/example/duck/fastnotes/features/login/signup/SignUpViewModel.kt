package com.example.duck.fastnotes.features.login.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.features.login.WelcomeBaseViewModel
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.PasswordValidationResult
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.isValidEmail
import com.example.duck.fastnotes.features.login.signup.ValidationUtils.isValidPassword
import com.example.duck.fastnotes.utils.ui.DialogState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): WelcomeBaseViewModel<SignUpState>(SignUpState.initialState()) {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    val showDialogEvent = Channel<DialogState>()

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
        reduce { state -> state.copy(isButtonEnabled = false) }

        val (email, password) = currentState

        if (!email.isValidEmail()) {
            reduce { it.copy(emailError = true, isButtonEnabled = true) }
            return false
        }

        val passwordValidationResult = validatePassword(password)
        if (passwordValidationResult !is PasswordValidationResult.Success) {
            reduce { it.copy(passwordValidationResult = passwordValidationResult, isButtonEnabled = true) }
            return false
        }

        return signUpWithEmail(email, password)
    }

    private fun validatePassword(password: String): PasswordValidationResult {
        return password.isValidPassword()
    }

    private suspend fun signUpWithEmail(email: String, password: String): Boolean {
        return try {
            Log.d("DDebug", "Try in signUp: $email - $password")
            auth.createUserWithEmailAndPassword(email, password).await()
            reduce { state -> state.copy(isButtonEnabled = true) }
            true
        } catch (e: Exception) {
            Log.d("DDebug", "Catch in signUp: ${e.localizedMessage}")
            reduce { state -> state.copy(isButtonEnabled = true) }
            showDialogEvent.send(DialogState.show(e.localizedMessage ?: ""))
            false
        }
    }

    fun onCloseDialog() {
        viewModelScope.launch {
            showDialogEvent.send(DialogState.close())
        }
    }
}

data class SignUpState(
    val email: String,
    val password: String,
    val emailError: Boolean,
    val passwordValidationResult: PasswordValidationResult,
    val isButtonEnabled: Boolean = true
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