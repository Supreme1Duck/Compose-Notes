package com.example.duck.fastnotes.manager

import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.database.TaskDao
import com.example.duck.fastnotes.domain.repository.TasksRepository
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TasksRepositoryManager(
    private val dao: TaskDao
) : TasksRepository {

    override fun getAllNotes(): Flow<List<TaskItem>> {
        return dao.getNotes().map {
            it.map { it.toTaskItem() }
        }
    }

    override fun getNotesByColor(color: ColorTypeWrapper): Flow<List<TaskItem>> {
        return dao.getNotes().map {
            it.map { it.toTaskItem() }
        }
    }

    override suspend fun insertNote(task: TaskItem) {
        dao.insertTask(task.toTask())
    }

//    override suspend fun deleteNote(key: Int) {
//        dao.deleteTask(key)
//    }
}