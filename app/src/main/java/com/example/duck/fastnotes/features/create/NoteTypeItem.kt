package com.example.duck.fastnotes.features.create

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteTypeItem(name: String, color: Color, isActive: Boolean, action: () -> Unit) {
    Card(shape = RoundedCornerShape(15.dp),
        backgroundColor = if (isActive) color else Color.White,
        elevation = 5.dp,
        onClick = action
    ) {
        Text(
            text = name,
            style = FastNotesTypography.subtitle1.copy(
                color = if (isActive) Color.White else BlackColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(Dimens.SMALL_MARGIN)
        )
    }
}

