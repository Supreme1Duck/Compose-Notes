package com.example.duck.fastnotes.domain

import androidx.compose.ui.graphics.Color

data class TaskDetailItem (
    val name: String,
    val description: String,
    val time: TimeLine,
    val color: Color,

    val inactive: Boolean,
)