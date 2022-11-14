package com.example.duck.fastnotes.features.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.manager.UserInfoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userInfoManager: UserInfoRepository
) : ViewModel() {

    private val notesCounter = MutableLiveData(0)

    init {
        viewModelScope.launch {
            userInfoManager.isRegistered.collect {
                Log.d("DDebug", "$it")
                val isRegistered = it
            }
        }
    }
}