package com.example.duck.fastnotes.database

import androidx.room.*
import com.example.duck.fastnotes.data.Task
import com.example.duck.fastnotes.data.TypeItem
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getNotes(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE color = :color")
    fun getNotesByColor(color: ColorTypeWrapper): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(key: Int)
}