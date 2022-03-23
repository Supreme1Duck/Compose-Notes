package com.example.duck.fastnotes.data

import org.joda.time.DateTime

data class TaskItem(
    val id: Int = 0,
    val name: String,
    val body: String,
    val type: String,
    val date: DateTime?,
    val time: DateTime?
) {
    fun toTask(): Task{
        return Task(
            id,
            name,
            body,
            type,
            date,
            time
        )
    }
}