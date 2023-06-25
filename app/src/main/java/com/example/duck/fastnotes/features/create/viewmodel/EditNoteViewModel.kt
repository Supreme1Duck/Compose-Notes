package com.example.duck.fastnotes.features.create.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.InvalidTaskItem
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.domain.usecase.EditNoteUseCase
import com.example.duck.fastnotes.features.core.BaseViewModel
import com.example.duck.fastnotes.features.create.data.NoteType
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
    savedStateHandle: SavedStateHandle,
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

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkCanDone(): Boolean = uiState.title.isNotBlank() && (uiState.type !is NoteType.Default)

    fun checkCanDelete(): Boolean = uiState.title.isNotBlank()

    private fun getNoteType(label: String): NoteType {
        return when (label) {
            NoteType.EveryDay.label -> NoteType.EveryDay
            NoteType.Priority.LABEL -> NoteType.Priority()
            NoteType.OneTime.label -> NoteType.OneTime
            NoteType.Soon.label -> NoteType.Soon
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

    fun onPrioritySubtasksChanged(subTasks: List<SubTask>) {
        reduce {
            it.copy(type = (it.type as NoteType.Priority).copy(subTasks = subTasks))
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
        val type: NoteType,
    ) {
        companion object {
            fun initial() = UIState("", "", NoteType.Default)
        }
    }
}
