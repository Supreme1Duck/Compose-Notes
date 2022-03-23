package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TasksRepository {

    fun getAllNotes() : Flow<List<TaskItem>>

    fun getNotesByColor(color: ColorTypeWrapper) : Flow<List<TaskItem>>

    suspend fun insertNote(task: TaskItem)

//    suspend fun deleteNote(key: Int)
}