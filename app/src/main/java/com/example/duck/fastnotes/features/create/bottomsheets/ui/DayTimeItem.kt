package com.example.duck.fastnotes.features.create.bottomsheets.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.domain.data.Days
import com.example.duck.fastnotes.ui.theme.MainTheme

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun DayTimeItem(day: Days = Days.Monday, isSelected: Boolean = false, onUncheck: () -> Unit = {}, onClicked: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .size(60.dp)
            .combinedClickable(
                onClick = onClicked,
                onLongClick = {
                    Log.d("DDebug", "On Long Click")
                    onUncheck()
                }
            ),
        border = BorderStroke(width = 1.5.dp, if (isSelected) Color.Green else Color.Gray),
        colors = CardDefaults.cardColors(containerColor = MainTheme.colors.secondary),
        shape = RoundedCornerShape(10.dp),
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = day.dayNameId).take(3),
                textAlign = TextAlign.Center,
                color = if (isSelected) Color.Green else Color.Gray,
                style = MainTheme.typography.h2.copy(fontWeight = FontWeight.W400, fontSize = 17.sp)
            )
        }
    }
}