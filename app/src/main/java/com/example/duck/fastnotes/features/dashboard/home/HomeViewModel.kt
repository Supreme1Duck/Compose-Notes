package com.example.duck.fastnotes.features.dashboard.home

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.NotesUseCase
import com.example.duck.fastnotes.usecase.GetUserInfoUseCase
import com.example.duck.fastnotes.utils.ui.BaseViewModel
import com.example.duck.fastnotes.utils.ui.UiEvent
import com.example.duck.fastnotes.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: NotesUseCase,
    private val userInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<HomeState, HomeScreenEvents>() {

    private val reducer : HomeScreenReducer = HomeScreenReducer(HomeState.initial())

    override val state: StateFlow<HomeState> = reducer.state

    init {
        getInitialData()
    }

    fun dismissDialog() {
        reducer.sendEvent(HomeScreenEvents.DismissDialog)
    }

    private fun getInitialData() {

    }

    private fun getTasks() {
        useCase.getTasks()
            .onEach {
                reducer.sendEvent(HomeScreenEvents.ShowList(it ?: emptyList()))
            }
            .launchIn(viewModelScope)
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            try {
                val result = userInfoUseCase()
                withContext(Dispatchers.Main) {
                    reducer.sendEvent(
                        HomeScreenEvents.ShowUserData(
                            name = result.login
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("Villain", "HomeViewModel getUserInfo e: ${e.printStackTrace()}")
            }
        }
    }
}

@Stable
data class HomeState (
    val userName: String?,
    val isPremium: Boolean,
    val taskList: List<NoteItem>?,
    val isLoading: Boolean,
    val isShowDialog: Boolean
): UiState {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                userName = null,
                taskList = null,
                isLoading = false,
                isPremium = false,
                isShowDialog = false
            )
        }
    }
}

sealed interface HomeScreenEvents : UiEvent {
    data class ShowList(val list: List<NoteItem>): HomeScreenEvents
    data class ShowUserData(val name: String): HomeScreenEvents
    data class EditItem(val id: Int): HomeScreenEvents
    object AddItem: HomeScreenEvents
    object ShowLoading: HomeScreenEvents
    object ShowDialog: HomeScreenEvents
    object DismissDialog: HomeScreenEvents
}