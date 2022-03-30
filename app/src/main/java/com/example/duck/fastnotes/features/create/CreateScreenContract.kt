package com.example.duck.fastnotes.features.create

sealed class CreateScreenContract {

    object OnSaveItem : CreateScreenContract()
    data class OnTypeChanged(val label: String) : CreateScreenContract()
    data class OnEnteredContent(val text: String): CreateScreenContract()
    data class OnEnteredBody(val text: String): CreateScreenContract()
}