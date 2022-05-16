package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.data.NoteItem
import com.example.duck.fastnotes.domain.repository.NotesRepository

class InsertNote(
    private val repository: NotesRepository
){
    suspend operator fun invoke(task: NoteItem){
        repository.insertNote(task)
    }
}