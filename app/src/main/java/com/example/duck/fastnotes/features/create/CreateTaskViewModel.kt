package com.example.duck.fastnotes.features.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.data.InvalidTaskItem
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.usecase.TasksUseCase
import com.example.duck.fastnotes.utils.Common.UNDEFINED_NOTE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    val useCase: TasksUseCase
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var body by mutableStateOf("")
        private set

    var canDone by mutableStateOf(false)

    var noteType = mutableStateOf<NoteType?>(null)

    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun checkCanDone() {
        canDone = if (noteType.value == null) false
        else title.isNotBlank()
    }

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

    @Throws(IllegalArgumentException::class)
    private fun getResult(): TaskItem {
        return if (canDone) TaskItem(
            name = title,
            body = body,
            type = noteType.value?.label ?: UNDEFINED_NOTE_TYPE,
            date = null,
            time = null
        )
        else throw InvalidTaskItem("More info required!")
    }

    fun onEvent(event: CreateScreenContract) {
        when (event) {
            CreateScreenContract.OnSaveItem -> {
                viewModelScope.launch {
                    try {
                        useCase.insertTask(getResult())
                        _eventFlow.emit(UIState.SaveNote)
                    } catch (e: InvalidTaskItem) {
                        _eventFlow.emit(UIState.ShowSnackbar("Fulfill all required fields!"))
                    }
                }
            }
            is CreateScreenContract.OnTypeChanged -> {
                noteType.value = getNoteType(event.label)
                checkCanDone()
            }
            is CreateScreenContract.OnEnteredContent -> {
                title = event.text
                checkCanDone()
            }
            is CreateScreenContract.OnEnteredBody -> {
                body = event.text
            }
        }
    }

    sealed class UIState {
        class ShowSnackbar(val message: String) : UIState()
        object SaveNote : UIState()
    }
}