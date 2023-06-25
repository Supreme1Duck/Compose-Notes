package com.example.duck.fastnotes.features.create.data

import androidx.compose.ui.graphics.Color
import java.io.Serializable

object BasicTypes {

    fun getBasicNotes() = listOf(
        NoteType.EveryDay,
        NoteType.Priority(),
        NoteType.OneTime,
        NoteType.Soon
    )
}

data class ColorTypeWrapper(val value: Color) : Serializable