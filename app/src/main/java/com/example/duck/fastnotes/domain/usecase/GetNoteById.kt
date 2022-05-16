package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.domain.repository.NotesRepository

class GetNoteById(val repository: NotesRepository) {
    suspend operator fun invoke(id: Int){
        repository.deleteNote(id)
    }
}