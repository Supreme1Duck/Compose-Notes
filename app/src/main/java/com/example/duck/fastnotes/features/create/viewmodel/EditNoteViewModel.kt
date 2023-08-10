package com.example.duck.fastnotes.features.create.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.Days
import com.example.duck.fastnotes.domain.data.InvalidTaskItem
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.data.NoteTime
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.domain.usecase.EditNoteUseCase
import com.example.duck.fastnotes.features.core.BaseViewModel
import com.example.duck.fastnotes.features.create.data.NoteType
import com.example.duck.fastnotes.utils.Common.CREATE_NOTE_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val useCase: EditNoteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EditNoteViewModel.UIState>(UIState.initial()) {

    // Note types storing data

    private var priorityNoteType = NoteType.Priority()
    private var everyDayNoteType = NoteType.EveryDay()

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
            NoteType.EveryDay.LABEL -> everyDayNoteType
            NoteType.Priority.LABEL -> priorityNoteType
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
            it.copy(type = getNoteType(type), isSubInfoShown = false)
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

    fun onSubTasksClicked() {
        reduce {
            it.copy(isSubInfoShown = false)
        }
    }

    fun onBottomSheetCancel() {
        reduce {
            it.copy(isSubInfoShown = true)
        }
    }

    fun onNoteTimeChanged() {
//        everyDayNoteType = everyDayNoteType.copy(noteTime = noteTime)

        reduce {
            it.copy(isSubInfoShown = true)
        }
    }

    fun onPrioritySubtasksChanged(subTasks: List<SubTask>) {
        val subTaskList = subTasks.toMutableList()

        priorityNoteType = priorityNoteType.copy(subTasks = subTaskList)

        reduce {
            it.copy(type = (it.type as NoteType.Priority).copy(subTasks = subTaskList), isSubInfoShown = true)
        }
    }

    sealed class UIEvent {
        class ShowSnackbar(val message: String = CREATE_NOTE_ERROR) : UIEvent()
        object SaveNote : UIEvent()
    }

    data class UIState(
        val title: String,
        val description: String,
        val type: NoteType,
        val isSubInfoShown: Boolean = false
    ) {
        companion object {
            fun initial() = UIState("", "", NoteType.Default, isSubInfoShown = false)
        }
    }

    private companion object {
        const val NOTE_KEY = "noteId"
        const val TAG = "EditNoteViewModel"
    }
}
