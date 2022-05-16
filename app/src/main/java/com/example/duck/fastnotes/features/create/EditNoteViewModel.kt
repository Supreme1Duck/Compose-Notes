package com.example.duck.fastnotes.features.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.data.InvalidTaskItem
import com.example.duck.fastnotes.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.EditNoteUseCase
import com.example.duck.fastnotes.utils.Common.CREATE_NOTE_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
        private val useCase: EditNoteUseCase,
        savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val id = savedStateHandle.get<String>("noteId")
    }

    var title by mutableStateOf("")
        private set

    var body by mutableStateOf("")
        private set

    var noteType = mutableStateOf<NoteType>(NoteType.Default)

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkCanDone() : Boolean = title.isNotBlank()

    fun checkCanDelete() : Boolean = title.isNotBlank()

    private fun getNoteType(label: String): NoteType {
        return when (label) {
            NoteType.Personal.label -> NoteType.Personal
            NoteType.Health.label -> NoteType.Health
            NoteType.Work.label -> NoteType.Work
            NoteType.Entertainment.label -> NoteType.Entertainment
            NoteType.Education.label -> NoteType.Education
            NoteType.Shopping.label -> NoteType.Shopping
            NoteType.Sport.label -> NoteType.Sport
            else -> throw IllegalArgumentException("Invalid note type!")
        }
    }

    private fun getResult(): NoteItem {
        return NoteItem(
            name = title,
            body = body,
            type = noteType.value.label,
            date = null,
            time = null
        )
    }

    fun onEvent(event: EditNoteScreenContract) {
        when (event) {
            EditNoteScreenContract.OnSaveItem -> {
                viewModelScope.launch {
                    try {
                        useCase.insertNote(getResult())
                        _eventFlow.emit(UIState.SaveNote)
                    } catch (e: InvalidTaskItem) {
                        _eventFlow.emit(UIState.ShowSnackbar())
                    }
                }
            }
            is EditNoteScreenContract.OnTypeChanged -> {
                noteType.value = getNoteType(event.label)
                checkCanDone()
            }
            is EditNoteScreenContract.OnEnteredContent -> {
                title = event.text
                checkCanDone()
            }
            is EditNoteScreenContract.OnEnteredBody -> {
                body = event.text
            }
            EditNoteScreenContract.OnDeleteItem -> {
                // TODO
            }
        }
    }

    sealed class UIState {
        class ShowSnackbar(val message: String = CREATE_NOTE_ERROR) : UIState()
        object SaveNote : UIState()
    }
}