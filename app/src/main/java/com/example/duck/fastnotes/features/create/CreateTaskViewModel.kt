package com.example.duck.fastnotes.features.create

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateTaskViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val color = MutableLiveData(ColorTypeWrapper(Color.Unspecified))

    fun setColor(value: Color){
        color.value = ColorTypeWrapper(value)
    }

    fun getResult(): NoteType{
        return NoteType(
            title.value ?: "",
            body.value,
            color = color.value ?: ColorTypeWrapper(Color.Unspecified)
        )
    }
}