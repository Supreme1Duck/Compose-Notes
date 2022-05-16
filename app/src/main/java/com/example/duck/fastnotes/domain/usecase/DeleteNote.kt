package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.domain.repository.NotesRepository

class DeleteNote(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(key: Int){
        repository.deleteNote(key)
    }
}