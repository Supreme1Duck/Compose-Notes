package com.example.duck.fastnotes.features.create

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.ui.theme.*

data class NoteType(
    val name: String,
    val body: String? = null,
    val color: ColorTypeWrapper,
    val date: String? = null,
    val time: String? = null
)
