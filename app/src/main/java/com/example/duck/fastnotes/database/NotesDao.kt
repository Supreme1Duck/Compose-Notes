package com.example.duck.fastnotes.database

import androidx.room.*
import com.example.duck.fastnotes.data.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE type = :type")
    fun getNotesByColor(type: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE uuid = :noteId")
    suspend fun getNoteById(noteId: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(note: Note)

    @Query("DELETE FROM notes WHERE uuid = :key")
    suspend fun deleteTask(key: Int)
}