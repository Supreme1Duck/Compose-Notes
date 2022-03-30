package com.example.duck.fastnotes.database

import androidx.room.TypeConverter
import org.joda.time.DateTime

class NoteTypeConverter {

    @TypeConverter
    fun fromDate(date: DateTime?): Long {
        return date?.millis ?: 0L
    }

    @TypeConverter
    fun toDate(date: Long): DateTime? {
        return if (date == 0L) null else DateTime(date)
    }
}