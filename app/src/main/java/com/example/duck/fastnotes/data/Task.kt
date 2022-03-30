package com.example.duck.fastnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val uuid: Int,
    val name: String,
    val body: String,
    val type: String,

    val date: DateTime?,
    val time: DateTime?
) {
    fun toTaskItem(): TaskItem {
        return TaskItem(
            uuid,
            name,
            body,
            type,
            date,
            time,
        )
    }
}