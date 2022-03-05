package com.example.duck.fastnotes.domain.model

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.domain.TimeLine

data class TaskDetailItem (
    val name: String,
    val description: String,
    val time: TimeLine,
    val color: Color,

    val inactive: Boolean,
)