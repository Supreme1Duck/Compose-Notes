package com.example.duck.fastnotes.features.dashboard.today

import androidx.compose.ui.graphics.Color
import com.example.duck.fastnotes.domain.data.TaskDetailItem

class DrawLineItem (
    val timeStart: String,
    val timeEnd: String,
    val color: Color,

    val hours: Int,
){

    fun TaskDetailItem.toLineItem(): DrawLineItem{
        return DrawLineItem(
            timeStart = time.timeStart.toString("h:mm"),
            timeEnd = time.timeEnd.toString("h:mm"),
            color = color,
            hours = Hours.hoursBetween(DateTime(time.timeStart), DateTime(time.timeEnd)).hours
        )
    }
}