package com.example.duck.fastnotes.features.create

import androidx.compose.ui.graphics.Color
import java.io.Serializable

object BasicTypes {

    fun getBasicNotes() = listOf(
        NoteType.Personal,
        NoteType.Health,
        NoteType.Work,
        NoteType.Entertainment,
        NoteType.Education,
        NoteType.Shopping,
        NoteType.Sport
    )
}

data class ColorTypeWrapper(val value: Color) : Serializable