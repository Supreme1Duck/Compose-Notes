package com.example.duck.fastnotes.data.db

import androidx.room.*
import com.example.duck.fastnotes.domain.model.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM n")
    fun getNotes(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem)

    @Delete
    suspend fun deleteTask(task: TaskItem)
}