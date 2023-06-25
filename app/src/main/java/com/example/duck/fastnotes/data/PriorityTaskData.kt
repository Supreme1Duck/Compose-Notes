package com.example.duck.fastnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "priority_tasks")
data class PriorityTaskData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val containingSubTasks: List<PrioritySubTask>,
    val done: Boolean,
    val data: Long
)