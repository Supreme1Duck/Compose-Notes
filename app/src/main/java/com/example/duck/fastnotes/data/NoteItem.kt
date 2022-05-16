package com.example.duck.fastnotes.data

import org.joda.time.DateTime

data class NoteItem(
    val id: Int = 0,
    val name: String,
    val body: String,
    val type: String,
    val date: DateTime?,
    val time: DateTime?
) {
    fun toTask(): Note{
        return Note(
            id,
            name,
            body,
            type,
            date,
            time
        )
    }
}

class InvalidTaskItem(message: String): Exception(message)