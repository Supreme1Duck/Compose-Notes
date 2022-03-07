package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.repository.TasksRepository

class InsertTask(
    private val repository: TasksRepository
){
    suspend operator fun invoke(task: TaskItem){
        repository.insertNote(task)
    }
}