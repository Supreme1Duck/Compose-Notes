package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.ui.WelcomeScreenSpacingComposition
import com.example.duck.fastnotes.ui.theme.*
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.Dimens.DEFAULT_MARGIN

@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val spacing = WelcomeScreenSpacingComposition.current.bottom

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = spacing)) {

        if (state.isLoading) {
            ShowLoading()
        } else {
            Title(userName = state.userName ?: "")

            if (state.isShowDialog)
                ErrorDialog(viewModel::dismissDialog)

            ShowTasks(state.taskList ?: emptyList()) {

            }
        }
    }
}

@Composable
fun Title(userName: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.LARGE_MARGIN, top = Dimens.SMALL_MARGIN)
    ) {
        Icon(
            Icons.Outlined.Person,
            contentDescription = stringResource(id = R.string.dashboard_app_bar_profile),
            Modifier
                .padding(start = DEFAULT_MARGIN)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(id = R.string.dashboard_app_bar_title, stringResource(id = R.string.dashboard_app_bar_empty_name)),
            Modifier.padding(start = DEFAULT_MARGIN),
            style = FastNotesTypography.h3
        )
    }
}

@Composable
fun ShowLoading() {

}

@Composable
fun ShowTasks(list: List<NoteItem>, onNoteClick: (id: Int) -> Unit) {
    Text(
        text = stringResource(id = R.string.dashboard_title_tasks),
        style = FastNotesTypography.subtitle1,
        modifier = Modifier.padding(
            start = DEFAULT_MARGIN,
            bottom = Dimens.SMALLER_MARGIN
        )
    )

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

    }
}

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.today_screen_error_dialog_title))
        },
        confirmButton = {
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.today_screen_error_dialog_retry))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.today_screen_dismiss_dialog))
            }
        },
        onDismissRequest = onDismiss
    )
}
