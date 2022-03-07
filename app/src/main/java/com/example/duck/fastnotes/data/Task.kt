package com.example.duck.fastnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import org.joda.time.DateTime

@Entity (tableName = "tasks")
data class Task (
    @PrimaryKey val uuid: Int,
    val name: String,
    val body: String,
    val color: ColorTypeWrapper,
    val date: DateTime?,
    val time: DateTime?
){
    fun toTaskItem(): TaskItem {
        return TaskItem(
            uuid,
            name,
            body,
            color,
            date,
            time,
        )
    }
}