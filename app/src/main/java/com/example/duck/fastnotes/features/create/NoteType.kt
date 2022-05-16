package com.example.duck.fastnotes.features.create

import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.*

sealed class NoteType(
    val label: String,
    val color: ColorTypeWrapper,
    val iconResource: Int
) {

    object Personal : NoteType("Personal", ColorTypeWrapper(PersonalNoteColor), R.drawable.ic_person)
    object Health : NoteType("Health", ColorTypeWrapper(HealthNoteColor), R.drawable.ic_health)
    object Work : NoteType("Work", ColorTypeWrapper(WorkNoteColor), R.drawable.ic_work)
    object Entertainment : NoteType("Entertainment", ColorTypeWrapper(RelaxNoteColor), R.drawable.ic_fun)
    object Education : NoteType("Education", ColorTypeWrapper(EducationNoteColor), R.drawable.ic_education)
    object Shopping : NoteType("Shopping", ColorTypeWrapper(ShoppingNoteColor), R.drawable.ic_shopping)
    object Sport : NoteType("Sport", ColorTypeWrapper(SportNoteColor), R.drawable.ic_sport)
    object Default : NoteType("Default", ColorTypeWrapper(DefaultNoteColor), -1)

}