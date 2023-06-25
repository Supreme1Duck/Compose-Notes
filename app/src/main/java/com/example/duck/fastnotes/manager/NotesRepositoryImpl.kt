package com.example.duck.fastnotes.manager

import com.example.duck.fastnotes.database.NotesDao
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.repository.NotesRepository
import com.example.duck.fastnotes.features.create.data.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class NotesRepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {

    override fun getAllNotes(): Flow<List<NoteItem>> {
        return dao.getNotes().map {
            it.map { it.toNoteItem() }
        }
    }

    override fun getNotesByColor(color: ColorTypeWrapper): Flow<List<NoteItem>> {
        return dao.getNotes().map {
            it.map { it.toNoteItem() }
        }
    }

    override suspend fun getNoteById(noteId: Int): NoteItem {
        return dao.getNoteById(noteId).toNoteItem()
    }

    override suspend fun insertNote(task: NoteItem) {
        dao.insertTask(task.toTask())
    }

    override suspend fun deleteNote(key: Int) {
        dao.deleteTask(key)
    }
}