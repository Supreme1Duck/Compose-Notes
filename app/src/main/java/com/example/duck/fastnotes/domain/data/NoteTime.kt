package com.example.duck.fastnotes.domain.data

import androidx.annotation.StringRes
import com.example.duck.fastnotes.R

data class NoteTime(
    val dates: Map<Days, DateTime>,
    val endDate: Long?
)

enum class Days(@StringRes val dayNameId: Int) {
    Monday(R.string.create_screen_info_monday),
    Tuesday(R.string.create_screen_info_tuesday),
    Wednesday(R.string.create_screen_info_wednesday),
    Thursday(R.string.create_screen_info_thursday),
    Friday(R.string.create_screen_info_friday),
    Saturday(R.string.create_screen_info_saturday),
    Sunday(R.string.create_screen_info_sunday),
    Undefined(R.string.create_screen_info_undefined)
}