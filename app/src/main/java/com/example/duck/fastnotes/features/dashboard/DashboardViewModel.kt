package com.example.duck.fastnotes.features.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.utils.ui.ObserverUtils.call
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userInfoManager: UserInfoRepository
) : ViewModel() {

    val notRegisteredEvent = Channel<Unit>()

    init {
        viewModelScope.launch {
            if (!userInfoManager.isRegistered()) {
                notRegisteredEvent.call()
            }
        }
    }

    private val notesCounter = MutableLiveData(0)
}