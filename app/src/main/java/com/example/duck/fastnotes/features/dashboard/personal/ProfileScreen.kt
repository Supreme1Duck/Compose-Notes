package com.example.duck.fastnotes.features.dashboard.personal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@Composable
fun ProfileScreen(viewModel: PersonalViewModel = viewModel()) {

    remember { viewModel.currentEditPosition }
    val text by remember { viewModel.state }
    val value = viewModel.currentItem
    val item by remember { viewModel.counter }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Dimens.BOTTOM_BAR_SIZE)
            .padding(horizontal = Dimens.DEFAULT_MARGIN),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = "${value?.name} - ${value?.age}",
            style = FastNotesTypography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = text,
            placeholder = { Text(text = text) },
            singleLine = true,
            onValueChange = viewModel::setTextState,
            modifier = Modifier.fillMaxWidth()
        )

        EditProfileButton(counter = item) {
            viewModel::incrementCounter.invoke()
        }
    }
}

@Composable
fun EditProfileButton(counter: Int, onClick: () -> Unit) {

    val enabled = counter < 10

    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = BlackColor),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = counter.toString(), style = FastNotesTypography.subtitle1.copy(color = Color.White))
    }

}