package com.example.duck.fastnotes.features.create

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.ui.theme.*
import java.io.Serializable

object BasicTypes {

    fun getBasicNotes() = listOf(
        NoteType("Personal", color = ColorTypeWrapper(PersonalNoteColor)),
        NoteType("Health", color = ColorTypeWrapper(HealthNoteColor)),
        NoteType("Work", color = ColorTypeWrapper(WorkNoteColor)),
        NoteType("Entertainment", color = ColorTypeWrapper(RelaxNoteColor)),
        NoteType("Education", color = ColorTypeWrapper(EducationNoteColor)),
        NoteType("Shopping", color = ColorTypeWrapper(ShoppingNoteColor)),
        NoteType("Sport", color = ColorTypeWrapper(SportNoteColor))
    )

    val basicTypes = mapOf(
        "Personal" to ColorTypeWrapper(PersonalNoteColor),
        "Health" to ColorTypeWrapper(WorkNoteColor),
        "Work" to ColorTypeWrapper(HealthNoteColor),
        "Entertainment" to ColorTypeWrapper(RelaxNoteColor),
        "Education" to ColorTypeWrapper(EducationNoteColor),
        "Shopping" to ColorTypeWrapper(SportNoteColor),
        "Sport" to ColorTypeWrapper(ShoppingNoteColor)
    )
}

data class ColorTypeWrapper(val value: Color): Serializable