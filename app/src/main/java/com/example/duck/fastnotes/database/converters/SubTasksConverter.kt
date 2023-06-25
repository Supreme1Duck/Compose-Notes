package com.example.duck.fastnotes.database.converters

import androidx.room.TypeConverter
import com.example.duck.fastnotes.data.PrioritySubTask
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class SubTasksConverter {

    private val moshi : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(List::class.java, PrioritySubTask::class.java)

    private val adapter: JsonAdapter<List<PrioritySubTask>> = moshi.adapter(type)


    @TypeConverter
    fun stringToSubTasks(json: String): List<PrioritySubTask>? = adapter.fromJson(json)

    @TypeConverter
    fun subtasksToString(subTask: List<PrioritySubTask>?): String = adapter.toJson(subTask)
}