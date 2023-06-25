package com.example.duck.fastnotes.features.create.bottomsheets.viewmodel

import androidx.lifecycle.ViewModel
import com.example.duck.fastnotes.domain.data.SubTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrioritySheetViewModel(subTasks: List<SubTask>?): ViewModel() {

    private val _prioritySubTasksItems = MutableStateFlow(subTasks?.toMutableList() ?: mutableListOf())
    val prioritySubTasksItems = _prioritySubTasksItems.asStateFlow()

    fun addSubTask(text: String) {
        _prioritySubTasksItems.value.add(SubTask(text))
    }

    fun editSubTask(text: String, position: Int) {
        _prioritySubTasksItems.value[position] = SubTask(text)
    }

    fun deleteSubTask(position: Int) {
        _prioritySubTasksItems.value.removeAt(position)
    }

    fun getSubTasks(): List<SubTask> = _prioritySubTasksItems.value
}