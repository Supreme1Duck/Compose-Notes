package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.features.create.ui.NoteTypeItem
import com.example.duck.fastnotes.utils.Dimens
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun ToggleGroup(
    options: List<Pair<String, Color>>,
    selectedOption: String?,
    modifier: Modifier,
    onClick: (String) -> Unit
) {

    FlowRow(
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimens.SMALL_MARGIN),
        mainAxisAlignment = MainAxisAlignment.Center
    ) {
        options.forEach {
            NoteTypeItem(name = it.first, color = it.second, selectedOption == it.first) {
                onClick(it.first)
            }
        }
    }
}
