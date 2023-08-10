package com.example.duck.fastnotes.features.create.bottomsheets.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.NoteTime
import com.example.duck.fastnotes.features.core.BottomSheetUI
import com.example.duck.fastnotes.features.create.bottomsheets.viewmodel.EveryDaySheetViewModel
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.utils.Dimens
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EveryDayBottomSheet(noteTime: NoteTime, onCancel: () -> Unit, onResult:() -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val viewModel: EveryDaySheetViewModel = rememberSaveable { EveryDaySheetViewModel() }

    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { false }
    )

    LaunchedEffect(key1 = true) {
        modalSheetState.show()
        viewModel.initialize(noteTime)
    }

    val clockDialogState = rememberUseCaseState(
        embedded = false
    )

    ClockDialog(
        state = clockDialogState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        selection = ClockSelection.HoursMinutes(
            onNegativeClick = {
                viewModel.onCalendarClosed()

                clockDialogState.hide()
            }
        ) { hours, minutes ->
            if (uiState.showTimePickerFrom)
                viewModel.onTimeStartSelected(hours, minutes)
            else viewModel.onTimeEndSelected(hours, minutes)

            clockDialogState.hide()
        },
    )

    if (uiState.showTimePickerFrom || uiState.showTimePickerTo)
        clockDialogState.show()

    BottomSheetUI(
        modalSheetState = modalSheetState,
        onDone = {
            onResult()
            coroutineScope.launch {
                modalSheetState.hide()
            }
        },
        onCancel = {
            onCancel()
            coroutineScope.launch {
                modalSheetState.hide()
            }
        }
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.create_screen_every_day_sheet_title),
            style = MainTheme.typography.h3,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MainTheme.spacing.default),
            text = stringResource(id = R.string.create_screen_every_day_sheet_description),
            style = MainTheme.typography.caption,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.SMALL_MARGIN),
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp
        ) {
            uiState.dates.forEach {
                Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    DayTimeItem(
                        it.key,
                        it.value != null,
                        onUncheck = {
                            viewModel.onUncheckDay(it.key)
                        }
                    ) {
                        viewModel.onDayClicked(it.key)
                    }

                    if (it.value != null) {
                        Text(
                            modifier = Modifier.padding(top = MainTheme.spacing.smaller),
                            text = stringResource(
                                id = R.string.create_screen_every_day_time_info,
                                it.value?.timeStart ?: "", it.value?.timeEnd ?: ""
                            ),
                            color = Color.Black,
                            fontSize = 8.sp
                        )
                    }
                }
            }
        }
    }
}