package com.example.duck.fastnotes.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.duck.fastnotes.data.Task
import com.example.duck.fastnotes.database.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao : TaskDao

    companion object{
        const val DB_NAME = "notes_db"
    }

}