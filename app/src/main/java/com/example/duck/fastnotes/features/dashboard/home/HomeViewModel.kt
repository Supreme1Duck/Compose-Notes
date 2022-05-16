package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: NotesUseCase
) : ViewModel() {

    var tasksList = mutableStateOf<List<NoteItem>>(emptyList())

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