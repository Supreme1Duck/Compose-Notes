package com.example.duck.fastnotes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.duck.fastnotes.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getNotes(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE type = :type")
    fun getNotesByColor(type: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

//    @Delete
//    suspend fun deleteTask(key: Int)
}