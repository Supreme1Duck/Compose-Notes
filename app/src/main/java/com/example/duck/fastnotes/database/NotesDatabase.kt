package com.example.duck.fastnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.duck.fastnotes.data.Note
import com.example.duck.fastnotes.data.PriorityTaskData
import com.example.duck.fastnotes.database.converters.NoteTypeConverter
import com.example.duck.fastnotes.database.converters.SubTasksConverter

@Database(entities = [Note::class, PriorityTaskData::class], version = 1)
@TypeConverters(NoteTypeConverter::class, SubTasksConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao : NotesDao

    companion object{
        const val DB_NAME = "notes_db"
    }
}