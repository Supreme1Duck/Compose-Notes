package com.example.duck.fastnotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.duck.fastnotes.data.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(NoteTypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao : TaskDao

    companion object{
        const val DB_NAME = "notes_db"
    }

}