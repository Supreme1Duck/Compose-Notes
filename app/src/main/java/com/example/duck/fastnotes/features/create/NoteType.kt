package com.example.duck.fastnotes.features.create

import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.*

sealed class NoteType(
    val label: String,
    val color: ColorTypeWrapper,
    val iconResource: Int
) {

    object Personal : NoteType("Персональные", ColorTypeWrapper(PersonalNoteColor), R.drawable.ic_person)
    object Health : NoteType("Здоровье", ColorTypeWrapper(HealthNoteColor), R.drawable.ic_health)
    object Work : NoteType("Работа", ColorTypeWrapper(WorkNoteColor), R.drawable.ic_work)
    object Entertainment : NoteType("Развлечения", ColorTypeWrapper(RelaxNoteColor), R.drawable.ic_fun)
    object Education : NoteType("Образование", ColorTypeWrapper(EducationNoteColor), R.drawable.ic_education)
    object Shopping : NoteType("Покупки", ColorTypeWrapper(ShoppingNoteColor), R.drawable.ic_shopping)
    object Sport : NoteType("Спорт", ColorTypeWrapper(SportNoteColor), R.drawable.ic_sport)
    object Default : NoteType("Default", ColorTypeWrapper(DefaultNoteColor), -1)

}