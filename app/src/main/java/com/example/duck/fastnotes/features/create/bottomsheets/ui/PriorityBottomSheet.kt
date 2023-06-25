package com.example.duck.fastnotes.features.create.bottomsheets.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.features.core.BottomSheetUI
import com.example.duck.fastnotes.features.create.bottomsheets.viewmodel.PrioritySheetViewModel
import kotlinx.coroutines.launch

/**
 * Bottom sheet to use in Priority Tasks for editing subtasks
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityBottomSheet(tasks: List<SubTask>, onResult: (List<SubTask>) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val viewModel = viewModel(initializer = {
        PrioritySheetViewModel(tasks)
    })

    val subTasks by viewModel.prioritySubTasksItems.collectAsState()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    BottomSheetUI(
        modalSheetState = modalSheetState,
        onDone = {
            onResult(subTasks)
            coroutineScope.launch {
                modalSheetState.hide()
            }
        },
        onCancel = {
            coroutineScope.launch {
                modalSheetState.hide()
            }
        }
    ) {

    }
}