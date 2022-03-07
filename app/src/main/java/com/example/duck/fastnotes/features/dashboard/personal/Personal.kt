package com.example.duck.fastnotes.features.dashboard.personal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens
import timber.log.Timber

@Composable
fun ProfileScreen(viewModel: PersonalViewModel = viewModel()) {

    val text by remember { viewModel.state }

    remember { viewModel.currentEditPosition }

    val value = viewModel.currentItem

    val onClickChange = viewModel::incrementItemsCount

    Timber.tag("AndrewDebug").d("$value")

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

        EditProfileButton(text = text) {
            onClickChange()
        }
    }
}

@Composable
fun EditProfileButton(text: String, onClick: () -> Unit) {

    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = BlackColor),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = text, style = FastNotesTypography.subtitle1.copy(color = Color.White))
    }

}