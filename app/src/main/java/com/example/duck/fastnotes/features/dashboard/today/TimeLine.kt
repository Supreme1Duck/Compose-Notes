package com.example.duck.fastnotes.features.dashboard.today

import com.example.duck.fastnotes.domain.TaskDetailItem

//List for date, sorted by Time
class TimeLine(private val list: List<TaskDetailItem>) {

    val timeLime = list.map { it.name to it.time }.toMap()

}