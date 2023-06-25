package com.example.duck.fastnotes.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrioritySubTask(
    val name: String,
    val isDone: Boolean
)