package com.example.duck.fastnotes.domain.usecase

class EditNoteUseCase(val getNoteById: GetNoteById, val insertNote: InsertNote, val deleteNote: DeleteNote)