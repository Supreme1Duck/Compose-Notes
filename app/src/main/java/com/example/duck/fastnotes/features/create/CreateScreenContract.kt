package com.example.duck.fastnotes.features.create

import androidx.compose.ui.focus.FocusState

sealed class CreateScreenContract {

    object OnSaveItem : CreateScreenContract()
    data class OnTypeChanged(val type: NoteType) : CreateScreenContract()
    data class OnChangeTitleFocus(val focus: FocusState) : CreateScreenContract()
    data class OnChangeContentFocus(val focus: FocusState) : CreateScreenContract()

}