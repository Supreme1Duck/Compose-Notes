package com.example.duck.fastnotes.features.create

sealed class EditNoteScreenContract {

    object OnSaveItem : EditNoteScreenContract()
    object OnDeleteItem : EditNoteScreenContract()
    data class OnTypeChanged(val label: String) : EditNoteScreenContract()
    data class OnEnteredContent(val text: String): EditNoteScreenContract()
    data class OnEnteredBody(val text: String): EditNoteScreenContract()
}