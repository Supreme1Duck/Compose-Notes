package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.domain.repository.TasksRepository

class DeleteTask(
    private val repository: TasksRepository
) {
//    suspend operator fun invoke(key: Int){
//        repository.deleteNote(key)
//    }
}