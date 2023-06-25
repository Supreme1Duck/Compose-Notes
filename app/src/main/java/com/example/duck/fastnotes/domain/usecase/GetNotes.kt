package com.example.duck.fastnotes.domain.usecase

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.data.OrderType
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.domain.repository.NotesRepository
import com.example.duck.fastnotes.features.create.data.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow

class GetNotes(
    private val repository: NotesRepository
) {
    operator fun invoke(
        orderType: OrderType = OrderType.Normal,
    ): Flow<List<NoteItem>?> {
        return when (orderType) {
            is OrderType.Color -> repository.getNotesByColor(orderType.color ?: ColorTypeWrapper(Color.Unspecified))

            OrderType.Normal -> repository.getAllNotes()

            OrderType.Time -> //Refactor Later
                repository.getAllNotes()
        }
    }
}