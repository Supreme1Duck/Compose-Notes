package com.example.duck.fastnotes.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.features.login.WelcomeNavigationViewModel.Companion.buttonClickCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class ButtonViewModel : ViewModel() {

    fun onButtonClick() {
        viewModelScope.launch {
            buttonClickCallback.onClick()
        }
    }
}