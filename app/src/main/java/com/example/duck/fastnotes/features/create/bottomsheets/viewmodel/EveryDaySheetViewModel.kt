package com.example.duck.fastnotes.features.create.bottomsheets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duck.fastnotes.domain.data.Days
import com.example.duck.fastnotes.domain.data.NoteTime
import com.example.duck.fastnotes.features.create.bottomsheets.data.UIDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class EveryDaySheetViewModel: ViewModel() {
    private var noteTimeMap = mutableMapOf<Days, UIDateTime?>(
        Days.Monday to null,
        Days.Tuesday to null,
        Days.Wednesday to null,
        Days.Thursday to null,
        Days.Friday to null,
        Days.Saturday to null,
        Days.Sunday to null
    )

    private val calendar = Calendar.getInstance()
    private var tempHoursFrom: Int = -1
    private var tempMinutesFrom: Int = -1

    private var tempDay : Days = Days.Undefined

    private var endDate: Long? = null

    private val _uiStateFlow = MutableStateFlow(UIState(noteTimeMap, endDate))
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun initialize(noteTime: NoteTime) {
        viewModelScope.launch(Dispatchers.Default) {
            noteTime.dates.forEach {
                noteTimeMap[it.key] = it.value.run {
                    calendar.time = Date(this.timeTo)
                    val hoursStart = calendar.get(Calendar.HOUR_OF_DAY)
                    val minutesStart = calendar.get(Calendar.MINUTE)
                    calendar.time = Date(this.timeFrom)
                    val hoursEnd = calendar.get(Calendar.HOUR_OF_DAY)
                    val minutesEnd = calendar.get(Calendar.MINUTE)
                    UIDateTime(getTime(hoursStart, minutesStart), getTime(hoursEnd, minutesEnd))
                }
            }
            endDate = noteTime.endDate


            produceUIState()
        }
    }

    fun onDayClicked(day: Days) {
        tempDay = day

        _uiStateFlow.value = _uiStateFlow.value.copy(
            showTimePickerFrom = true
        )
    }

    fun onUncheckDay(day: Days) {
        val tempMap = _uiStateFlow.value.dates.toMutableMap()
        tempMap[day] = null

        _uiStateFlow.value = _uiStateFlow.value.copy(dates = tempMap)
    }

    fun onTimeStartSelected(hourOfDay: Int, minute: Int) {
        tempHoursFrom = hourOfDay
        tempMinutesFrom = minute

        _uiStateFlow.value = _uiStateFlow.value.copy(showTimePickerFrom = false, showTimePickerTo = true)
    }

    fun onTimeEndSelected(hourOfDay: Int, minute: Int) {
        val tempMap = _uiStateFlow.value.dates.toMutableMap()

        if (checkIfDataValid()) {
            tempMap[tempDay] = UIDateTime(getTime(tempHoursFrom, tempMinutesFrom), getTime(hourOfDay, minute))
        }

        _uiStateFlow.value = _uiStateFlow.value.copy(dates = tempMap)

        onCalendarClosed()

        clearTempData()
    }

    fun onCalendarClosed() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            showTimePickerFrom = false,
            showTimePickerTo = false
        )

        clearTempData()
    }

    private fun checkIfDataValid(): Boolean {
        return tempDay != Days.Undefined && tempHoursFrom >= 0 && tempMinutesFrom >= 0
    }

    private fun getTime(hours: Int, minute: Int): String {
        return if (minute < 10) {
            "$hours.0$minute"
        } else {
            "$hours.$minute"
        }
    }

    private fun produceUIState() {
        _uiStateFlow.value = UIState(noteTimeMap, endDate)
    }

    private fun clearTempData() {
        tempDay = Days.Undefined

        tempHoursFrom = -1
        tempMinutesFrom = -1
    }

    data class UIState(
        val dates: Map<Days, UIDateTime?>,
        val endDate: Long?,
        val showTimePickerFrom: Boolean = false,
        val showTimePickerTo: Boolean = false
    )
}