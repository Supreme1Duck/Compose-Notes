package com.example.duck.fastnotes.data

import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import org.joda.time.DateTime

data class TaskItem(
    val id: Int,
    val name: String,
    val body: String,
    val color: ColorTypeWrapper,
    val date: DateTime?,
    val time: DateTime?
)