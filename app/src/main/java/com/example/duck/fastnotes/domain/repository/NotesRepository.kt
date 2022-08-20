package com.example.duck.fastnotes.domain.repository

import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getAllNotes() : Flow<List<NoteItem>>

    fun getNotesByColor(color: ColorTypeWrapper) : Flow<List<NoteItem>>

    suspend fun getNoteById(noteId: Int): NoteItem

    suspend fun insertNote(task: NoteItem)

    suspend fun deleteNote(key: Int)
}