package com.example.duck.fastnotes.features.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.NotesUseCase
import com.example.duck.fastnotes.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: NotesUseCase,
    private val userInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    var homeState = MutableStateFlow<HomeState>(HomeState.Loading)
        private set

    val userName: Flow<String> = flow {
        userInfoUseCase().onSuccess {
            emit(it.userName)
        }
    }

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            useCase.getTasks()
                .distinctUntilChanged()
                .debounce(300L)
                .collect {
                    homeState.emit(HomeState.DisplayTasks(it ?: emptyList()))
                }
        }
    }

    private fun getUserInfo(){

    }
}

sealed class HomeState {

    class DisplayTasks(val tasks: List<NoteItem>) : HomeState()
    class Error(val message: String) : HomeState()

    object OnErrorRetry : HomeState()

    object Loading : HomeState()
}