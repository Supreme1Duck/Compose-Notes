package com.example.duck.fastnotes.features.create

data class NoteType(
    val name: String,
    val body: String? = null,
    val color: ColorTypeWrapper,
    val date: String? = null,
    val time: String? = null
)
