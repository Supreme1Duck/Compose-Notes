package com.example.duck.fastnotes.features.create.data

import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.ui.theme.*

sealed class NoteType(
    val label: String,
    val color: ColorTypeWrapper,
    val iconResource: Int
) {

    object EveryDay : NoteType("Every Day", ColorTypeWrapper(PersonalNoteColor), R.drawable.ic_person)

    data class Priority(
        val subTasks: List<SubTask>? = null,
    ) : NoteType("Priority", ColorTypeWrapper(PriorityTaskColor), R.drawable.ic_health) {
        companion object {
            const val LABEL = "Priority"
        }
    }

    object OneTime : NoteType("One Time", ColorTypeWrapper(WorkNoteColor), R.drawable.ic_work)
    object Soon : NoteType("Nearest", ColorTypeWrapper(RelaxNoteColor), R.drawable.ic_fun)
    object Default : NoteType("Default", ColorTypeWrapper(DefaultNoteColor), -1)
}