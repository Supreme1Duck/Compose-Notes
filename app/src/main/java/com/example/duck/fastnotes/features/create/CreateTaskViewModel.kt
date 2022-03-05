package com.example.duck.fastnotes.features.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.lang.Exception
import java.lang.IllegalArgumentException

class CreateTaskViewModel : ViewModel() {

    var title by mutableStateOf("")
        private set

    var body by mutableStateOf("")
        private set

    var color by mutableStateOf(ColorTypeWrapper(Color.Unspecified))
        private set

    var canDone by mutableStateOf(false)

    fun setTitleText(text: String) {
        title = text
        checkCanDone()
    }

    fun setBodyText(text: String) {
        body = text
    }

    fun setColorType(value: ColorTypeWrapper?) {
        value?.let {
            color = it
            checkCanDone()
        }
    }

    private fun checkCanDone() {
        canDone = if (color.value == Color.Unspecified) false
        else title.isNotBlank()
    }

    fun getResult(): NoteType? {
        return if (canDone) NoteType(title, body, color) else null
    }
}