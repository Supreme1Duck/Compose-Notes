package com.example.duck.fastnotes.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TypeItem (
        val title: String,
        val imageSrc: ImageVector,
        val color: Color,

        val estimate: Int,
        val inactive: Int
)