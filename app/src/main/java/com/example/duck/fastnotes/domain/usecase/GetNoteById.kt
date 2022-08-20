package com.example.duck.fastnotes.domain.usecase

import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.repository.NotesRepository
import kotlin.jvm.Throws

class GetNoteById(val repository: NotesRepository) {
    @Throws(IllegalArgumentException::class)
    suspend operator fun invoke(id: Int?): NoteItem {
        return if (id != null)
            repository.getNoteById(id)
        else
            throw IllegalArgumentException("Illegal note item")
    }
}