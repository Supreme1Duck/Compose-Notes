package com.example.duck.fastnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.duck.fastnotes.data.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteTypeConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao : NotesDao

    companion object{
        const val DB_NAME = "notes_db"
    }
}