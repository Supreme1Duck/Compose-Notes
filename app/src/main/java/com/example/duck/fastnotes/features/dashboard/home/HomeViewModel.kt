package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: TasksUseCase
) : ViewModel() {

    var tasksList = mutableStateOf<List<TaskItem>>(emptyList())

    init {
        getTasks()

    }

    private fun getTasks() {
        viewModelScope.launch {
            useCase.getTasks()
                .onEach {
                    tasksList.value = it ?: emptyList()
                }
                .launchIn(viewModelScope)
        }
    }
}