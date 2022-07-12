package com.example.duck.fastnotes.v2

import kotlinx.coroutines.flow.Flow

class ViewUtils {
    interface HandleBackView {
        val onBackClicked: Flow<Unit>
    }
}
