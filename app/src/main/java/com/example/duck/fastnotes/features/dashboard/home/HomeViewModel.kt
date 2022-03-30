package com.example.duck.fastnotes.features.dashboard.home

import androidx.lifecycle.ViewModel
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: TasksUseCase
) : ViewModel() {

    var tasksList: Flow<List<TaskItem>?> = useCase.getTasks()
        private set

}