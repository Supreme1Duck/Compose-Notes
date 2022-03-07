package com.example.duck.fastnotes.features.dashboard.personal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PersonalViewModel : ViewModel() {

    val state = mutableStateOf("State will be here")

    var currentEditPosition by mutableStateOf(-1)

    var items = mutableStateListOf(
        MockStructure("Andrew", 22),
        MockStructure("2 - Debrew", 23),
        MockStructure("3 - Fabrew", 25),
        MockStructure("4 - Andrew", 27),
        MockStructure("5 - Andrew", 29),
    )
        private set

    val currentItem: MockStructure?
        get() = items.getOrNull(currentEditPosition)

    fun setTextState(text: String) {
        state.value = text
    }

    fun incrementItemsCount(){
        currentEditPosition += 1
    }
}