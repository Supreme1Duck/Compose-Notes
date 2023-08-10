package com.example.duck.fastnotes.features.create.bottomsheets.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.SubTask
import com.example.duck.fastnotes.features.core.BottomSheetUI
import com.example.duck.fastnotes.features.create.bottomsheets.viewmodel.PrioritySheetViewModel
import com.example.duck.fastnotes.ui.theme.MainTheme
import kotlinx.coroutines.launch

/**
 * Bottom sheet to use in Priority Tasks for editing subtasks
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityBottomSheet(tasks: List<SubTask>, onCancel: () -> Unit, onResult: (List<SubTask>) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val viewModel = viewModel<PrioritySheetViewModel>()

    LaunchedEffect(key1 = true) {
        viewModel.initialize(tasks)
    }

    val subTasks by viewModel.prioritySubTasksItems.collectAsStateWithLifecycle()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { false },
        skipHalfExpanded = true,
    )

    BottomSheetUI(
        modalSheetState = modalSheetState,
        onDone = {
            coroutineScope.launch {
                modalSheetState.hide()
                onResult(subTasks)
            }
        },
        onCancel = {
            coroutineScope.launch {
                modalSheetState.hide()
                onCancel()
            }
        }
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.create_screen_add_subtasks),
            style = MainTheme.typography.h3,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MainTheme.spacing.default),
            text = stringResource(id = R.string.create_screen_info_subtasks),
            style = MainTheme.typography.caption,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        LazyColumn(contentPadding = PaddingValues(vertical = MainTheme.spacing.default)) {
            itemsIndexed(subTasks) { index, subTask ->
                PriorityCreateItem(subTask,
                    onConfirmCreation = viewModel::addSubTask,
                    modifyExisting = {
                        viewModel.editSubTask(it, index)
                    },
                    onCancelCreation = {
                        viewModel.deleteSubTask(index)
                    }
                )
            }

            item {
                PriorityCreateItem(
                    isToCreate = true,
                    onConfirmCreation = viewModel::addSubTask
                )
            }
        }
    }
}