package com.example.duck.fastnotes.domain.usecase

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.data.OrderType
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.repository.TasksRepository
import com.example.duck.fastnotes.features.create.ColorTypeWrapper
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val repository: TasksRepository
) {
    operator fun invoke(
        orderType: OrderType = OrderType.Normal,
    ): Flow<List<TaskItem>?> {
        return when (orderType) {
            is OrderType.Color -> repository.getNotesByColor(orderType.color ?: ColorTypeWrapper(Color.Unspecified))

            OrderType.Normal -> repository.getAllNotes()

            OrderType.Time -> //Refactor Later
                repository.getAllNotes()
        }
    }

}