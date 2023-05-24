package com.example.duck.fastnotes.features.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.InvalidTaskItem
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.usecase.EditNoteUseCase
import com.example.duck.fastnotes.features.core.BaseViewModel
import com.example.duck.fastnotes.utils.Common.CREATE_NOTE_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val useCase: EditNoteUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<EditNoteViewModel.UIState>(UIState.initial()) {

    private companion object {
        const val NOTE_KEY = "noteId"
        const val TAG = "EditNoteViewModel"
    }

    val note: SharedFlow<NoteItem> = flow {
        val id = savedStateHandle.get<Int>(NOTE_KEY)
        emit(useCase.getNoteById(id))
    }.flowOn(Dispatchers.IO)
        .catch { Timber.tag(TAG).d(it.localizedMessage) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )

    init {
        viewModelScope.launch {
//            note.collect {
//
//            }
        }
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkCanDone(): Boolean = uiState.title.isNotBlank()

    fun checkCanDelete(): Boolean = uiState.title.isNotBlank()

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
            name = uiState.title,
            body = uiState.description,
            type = uiState.type.label,
            date = null,
            time = null
        )
    }

    fun onTitleChanged(title: String) {
        reduce {
            it.copy(title = title)
        }
        checkCanDone()
    }

    fun onDescriptionChanged(description: String) {
        reduce {
            it.copy(description = description)
        }
        checkCanDone()
    }

    fun onTypeChanged(type: String) {
        reduce {
            it.copy(type = getNoteType(type))
        }
        checkCanDone()
    }

    fun onSaveItem() {
        viewModelScope.launch {
            try {
                useCase.insertNote(getResult())
                _eventFlow.emit(UIEvent.SaveNote)
            } catch (e: InvalidTaskItem) {
                _eventFlow.emit(UIEvent.ShowSnackbar())
            }
        }
    }

    fun onDeleteItem() {

    }

    sealed class UIEvent {
        class ShowSnackbar(val message: String = CREATE_NOTE_ERROR) : UIEvent()
        object SaveNote : UIEvent()
    }

    data class UIState(
        val title: String,
        val description: String,
        val type: NoteType
    ) {
        companion object {
            fun initial() = UIState("", "", NoteType.Default)
        }
    }
}
