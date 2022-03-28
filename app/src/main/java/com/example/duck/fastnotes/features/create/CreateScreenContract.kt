package com.example.duck.fastnotes.features.create

import androidx.compose.ui.focus.FocusState

sealed class CreateScreenContract {

    object OnSaveItem : CreateScreenContract()
    data class OnTypeChanged(val label: String) : CreateScreenContract()
    data class OnChangeTitleFocus(val focus: FocusState) : CreateScreenContract()
    data class OnChangeContentFocus(val focus: FocusState) : CreateScreenContract()
    data class OnEnteredContent(val text: String): CreateScreenContract()
    data class OnEnteredBody(val text: String): CreateScreenContract()

}