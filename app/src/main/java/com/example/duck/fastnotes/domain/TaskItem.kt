package com.example.duck.fastnotes.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TaskItem (
        val title: String,
        val imageSrc: ImageVector,
        val color: Color,

        val source: List<TaskDetailItem>,

        val estimate: Int,
        val inactive: Int
)