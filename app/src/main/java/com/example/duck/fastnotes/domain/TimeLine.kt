package com.example.duck.fastnotes.domain

import org.joda.time.DateTime
import java.text.SimpleDateFormat

data class TimeLine(
    val timeStart: DateTime,
    val timeEnd: DateTime
){
    override fun toString(): String {
        return StringBuilder().apply {
            this.append(timeStart.toString("h:mm"))
                .append(" - ")
                .append(timeStart.toString("h:mm"))
        }.toString()
    }

    // Fun to display date with time
    fun toString(isDate: Boolean): String{
        return StringBuilder().apply {
            this.append(timeStart.toString("d, MMMM h:mm"))
                .append(" - ")
                .append(timeEnd.toString("d, MMMM h:mm"))
        }.toString()
    }
}
