package com.example.duck.fastnotes.features.create.data

import com.example.duck.fastnotes.domain.data.NoteTime
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.ui.theme.DefaultNoteColor
import com.example.duck.fastnotes.ui.theme.PersonalNoteColor
import com.example.duck.fastnotes.ui.theme.PriorityTaskColor
import com.example.duck.fastnotes.ui.theme.RelaxNoteColor
import com.example.duck.fastnotes.ui.theme.WorkNoteColor

sealed class NoteType(
    val label: String,
    val color: ColorTypeWrapper
) {

    data class EveryDay(
        val noteTime: NoteTime? = null
    ) : NoteType("Every Day", ColorTypeWrapper(PersonalNoteColor)) {
        companion object {
            const val LABEL = "Every Day"
        }
    }

    data class Priority(
        val subTasks: List<SubTask>? = null,
    ) : NoteType("Priority", ColorTypeWrapper(PriorityTaskColor)) {
        companion object {
            const val LABEL = "Priority"
        }
    }

    object OneTime : NoteType("One Time", ColorTypeWrapper(WorkNoteColor))
    object Soon : NoteType("Nearest", ColorTypeWrapper(RelaxNoteColor))
    object Default : NoteType("Default", ColorTypeWrapper(DefaultNoteColor))
}