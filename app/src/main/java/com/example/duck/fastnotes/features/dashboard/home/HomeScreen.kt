@file:OptIn(ExperimentalFoundationApi::class)

package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.ui.theme.*
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.Dimens.DEFAULT_MARGIN

@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val stateLoading by viewModel.showLoading.collectAsStateWithLifecycle(false)

    Column(
        Modifier
            .fillMaxSize()
    ) {
        if (stateLoading) {
            ShowLoading()
        } else {
            Title(userName = state.userName ?: "")

            ShowTasks(state.taskList ?: emptyList(), viewModel::onTaskClicked)
        }
    }
}

@Composable
fun Title(userName: String) {
    Column(Modifier.padding(horizontal = MainTheme.spacing.default)) {
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
                text = stringResource(id = R.string.dashboard_app_bar_title,
                    stringResource(id = R.string.dashboard_app_bar_empty_name)),
                Modifier.padding(start = DEFAULT_MARGIN),
                style = FastNotesTypography.h3
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MainTheme.colors.secondary,
        )
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(140.dp),
            color = BasePurple,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun ShowTasks(list: List<NoteItem>, onTaskClicked: (id: Int) -> Unit) {
    val context = LocalContext.current

//    if (list.isEmpty()) {
//
//        NoTasksInfo()
//
//        return
//    }
//
    Text(
        text = stringResource(id = R.string.dashboard_title_tasks),
        style = MainTheme.typography.caption.copy(color = MainTheme.colors.onPrimary, fontSize = 16.sp),
        modifier = Modifier.padding(
            top = MainTheme.spacing.default,
            start = MainTheme.spacing.default,
            bottom = Dimens.SMALLER_MARGIN
        )
    )

    BaseTasks(modifier = Modifier.padding(top = MainTheme.spacing.default))

//    LazyVerticalGrid(
//        modifier = Modifier.padding(top = MainTheme.spacing.default),
//        columns = GridCells.Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(MainTheme.spacing.small),
//        horizontalArrangement = Arrangement.spacedBy(MainTheme.spacing.small)
//    ) {
//        items(5) { index ->
//            NoteItem(index)
//        }
//    }
}

@Composable
fun BaseTasks(modifier: Modifier, taskList: List<Unit> = emptyList(), onTaskClicked: (id: Int) -> Unit = {}) {

    HorizontalPager(modifier = modifier, pageCount = 5, contentPadding = PaddingValues(start = MainTheme.spacing.default, end = MainTheme.spacing.extraLarge), pageSpacing = 12.dp) {
        BaseTask()
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

@Composable
fun NoTasksInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.padding(bottom = 50.dp),
            text = stringResource(id = R.string.dashboard_no_tasks_info)
        )

        Image(
            painter = painterResource(id = R.drawable.old),
            contentDescription = null,
        )
    }
}
