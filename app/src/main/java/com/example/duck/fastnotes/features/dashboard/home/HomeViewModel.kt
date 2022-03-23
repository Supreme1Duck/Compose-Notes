package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.data.OrderType
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: TasksUseCase
) : ViewModel() {

    var tasksList: Flow<List<TaskItem>?> = useCase.getTasks()
        private set

//    var orderType = mutableStateOf<OrderType>(OrderType.Normal)
//        private set

//    fun changeOrderType(newType: OrderType) {
//        orderType.value = newType
//        useCase.getTasks(orderType = orderType.value)
//    }

    fun deleteNote(id: Int) {
//        viewModelScope.launch {
//            useCase.deleteTask(id)
//        }
    }

    fun someFun() {
        val a = { a: Int, b: Int -> a * b * 3 }
    }

    fun insertNote(item: TaskItem) {
        viewModelScope.launch {
            useCase.insertTask(item)
        }
    }
}