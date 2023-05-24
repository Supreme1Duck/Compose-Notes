package com.example.duck.fastnotes.features.dashboard.home

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.NotesUseCase
import com.example.duck.fastnotes.features.core.BaseViewModel
import com.example.duck.fastnotes.usecase.GetUserInfoUseCase
import com.example.duck.fastnotes.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: NotesUseCase,
    private val userInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<HomeState>(HomeState.initial()) {

    init {
        viewModelScope.launch {
            showLoading()
            getTasks()
        }
    }

    private fun getTasks() {
        useCase.getTasks()
            .onEach { list ->
                delay(2000L)

                Log.d("DDebug", "$list")

                reduce {
                    it.copy(taskList = list)
                }

                stopLoading()
            }
            .launchIn(viewModelScope)
    }

    fun onTaskClicked(taskId: Int) {
        Log.d("DDebug", "Task clicked -> $taskId")
    }
}

@Stable
data class HomeState (
    val userName: String?,
    val isPremium: Boolean,
    val taskList: List<NoteItem>?,
    val isShowDialog: Boolean
): UiState {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                userName = null,
                taskList = null,
                isPremium = false,
                isShowDialog = false
            )
        }
    }
}