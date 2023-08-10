package com.example.duck.fastnotes.features.create.bottomsheets.viewmodel

import androidx.lifecycle.ViewModel
import com.example.duck.fastnotes.domain.data.SubTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrioritySheetViewModel: ViewModel() {

    private val _prioritySubTasksItems = MutableStateFlow(mutableListOf<SubTask>())
    val prioritySubTasksItems = _prioritySubTasksItems.asStateFlow()

    fun initialize(subTasks: List<SubTask>?) {
        _prioritySubTasksItems.value = subTasks?.toMutableList() ?: mutableListOf()
    }

    fun addSubTask(subTask: SubTask) {
        _prioritySubTasksItems.value = _prioritySubTasksItems.value.toMutableList().apply {
            add(subTask)
        }
    }

    fun editSubTask(title: String, position: Int) {
        _prioritySubTasksItems.value = _prioritySubTasksItems.value.toMutableList().apply {
            this[position] = get(position).copy(title = title)
        }
    }

    fun deleteSubTask(position: Int) {
        _prioritySubTasksItems.value = _prioritySubTasksItems.value.toMutableList().apply {
            removeAt(position)
        }
    }
}