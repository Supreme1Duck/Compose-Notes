package com.example.duck.fastnotes.features.login.signup

import android.util.Patterns
import java.util.regex.Pattern

object ValidationUtils {

    private val specialSymbolsRegex = Pattern.compile("[!\"#\$%&'()*+,’\\-./:;<>«»=?@\\[\\]{}\\\\^_`~|]")
    private val spaceRegex = Pattern.compile("\\s")
    private val min10Regex = Pattern.compile(".{10,}")

    fun CharSequence.isValidPassword(): PasswordValidationResult {
        return when {
            checkForSpecialSymbols() -> {
                PasswordValidationResult.PasswordValidationError.SpecialSymbolsError
            }
            checkForSpaces() -> {
                PasswordValidationResult.PasswordValidationError.SpaceError
            }
            checkForLess10() -> {
                PasswordValidationResult.PasswordValidationError.Less10
            }
            else -> {
                PasswordValidationResult.Success
            }
        }
    }

    private fun CharSequence.checkForSpecialSymbols(): Boolean {
        return specialSymbolsRegex.matcher(this).find()
    }

    private fun CharSequence.checkForSpaces(): Boolean {
        return spaceRegex.matcher(this).find()
    }

    private fun CharSequence.checkForLess10(): Boolean {
        return !min10Regex.matcher(this).matches()
    }

    fun CharSequence.isValidEmail(): Boolean {
        return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    sealed interface PasswordValidationResult {
        enum class PasswordValidationError : PasswordValidationResult {
            SpecialSymbolsError,
            SpaceError,
            Less10,
        }

        object Success : PasswordValidationResult
    }
}