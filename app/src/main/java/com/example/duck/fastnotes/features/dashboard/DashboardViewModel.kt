package com.example.duck.fastnotes.features.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userInfoManager: UserInfoRepository
) : ViewModel() {

    private val notesCounter = MutableLiveData(0)
}