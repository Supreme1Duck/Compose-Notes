package com.example.duck.fastnotes.features.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.utils.Common.UNDEFINED_NOTE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor() : ViewModel() {

    var title by mutableStateOf("")
        private set

    var body by mutableStateOf("")
        private set

    var canDone by mutableStateOf(false)

    var noteType = mutableStateOf<NoteType?>(null)

    fun setTitleText(text: String) {
        title = text
        checkCanDone()
    }

    fun setBodyText(text: String) {
        body = text
    }

    private fun checkCanDone() {
        canDone = if (noteType.value == null) false
        else title.isNotBlank()
    }

    fun setNoteType(label: String) {
        noteType.value = when (label) {
            NoteType.Personal.label -> NoteType.Personal
            NoteType.Health.label -> NoteType.Health
            NoteType.Work.label -> NoteType.Work
            NoteType.Entertainment.label -> NoteType.Entertainment
            NoteType.Education.label -> NoteType.Education
            NoteType.Shopping.label -> NoteType.Shopping
            NoteType.Sport.label -> NoteType.Sport
            else -> null
        }
    }

    fun getResult(): TaskItem {
        return if (canDone) TaskItem(
            name = title,
            body = body,
            type = noteType.value?.label ?: UNDEFINED_NOTE_TYPE,
            date = null,
            time = null
        )
        else throw IllegalArgumentException("More info required")
    }

    fun onEvent(event: CreateScreenContract) {
        when (event) {
            is CreateScreenContract.OnChangeContentFocus -> {

            }
            is CreateScreenContract.OnChangeTitleFocus -> {

            }
            CreateScreenContract.OnSaveItem -> {

            }
            is CreateScreenContract.OnTypeChanged -> {

            }
        }
    }
}