package com.example.duck.fastnotes.features.create.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.create.bottomsheets.ui.PriorityBottomSheet
import com.example.duck.fastnotes.features.create.data.BasicTypes.getBasicNotes
import com.example.duck.fastnotes.features.create.data.NoteType
import com.example.duck.fastnotes.features.create.viewmodel.EditNoteViewModel
import com.example.duck.fastnotes.features.dashboard.home.ToggleGroup
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Preview
@ExperimentalComposeUiApi
@Composable
fun EditNoteScreen(
    viewModel: EditNoteViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.uiStateFlow.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                EditNoteViewModel.UIEvent.SaveNote -> onNavigateBack()

                is EditNoteViewModel.UIEvent.ShowSnackbar -> {
                    launch {
                        keyboardController?.hide()
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }
    }

    ConstraintLayout(
        constraintSet = constraints, modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.DEFAULT_MARGIN)
    ) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.create_screen_back),
            modifier = Modifier
                .layoutId("back")
                .noRippleClickable {
                    onNavigateBack()
                }
        )

        Text(
            text = stringResource(id = R.string.create_screen_title), modifier = Modifier
                .layoutId("label")
                .fillMaxWidth()
                .padding(all = Dimens.DEFAULT_MARGIN),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )

        Icon(
            Icons.Filled.Done,
            contentDescription = stringResource(id = R.string.create_screen_done),
            modifier = Modifier
                .layoutId("done")
                .noRippleClickable(viewModel::onSaveItem)
                .alpha(if (!viewModel.checkCanDone()) 0.5f else 1f)
        )

        TextField(
            value = uiState.title,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_title)) },
            onValueChange = viewModel::onTitleChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier
                .layoutId("titleEditText")
                .fillMaxWidth()
                .padding(vertical = Dimens.DEFAULT_MARGIN)
        )

        TextField(
            value = uiState.description,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_body)) },
            onValueChange = viewModel::onDescriptionChanged,
            modifier = Modifier
                .layoutId("bodyEditText")
                .fillMaxWidth()
        )

        ToggleGroup(
            options = getBasicNotes().map { it.label to it.color.value },
            selectedOption = uiState.type.label,
            modifier = Modifier.layoutId("tags"),
            onClick = viewModel::onTypeChanged
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .layoutId("snackbar")
                .padding(bottom = Dimens.BOTTOM_BAR_SIZE)
        )

        uiState.type.let {
            if (it is NoteType.Priority && !it.subTasks.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("tasksCount")
                        .padding(top = MainTheme.spacing.default)
                        .noRippleClickable {
                            viewModel.onSubTasksClicked()
                        },
                    text = stringResource(
                        id = R.string.create_screen_subtasks_count,
                        it.subTasks.size
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (uiState.type is NoteType.Priority && !uiState.isSubInfoShown) {
        PriorityBottomSheet(
            tasks = (uiState.type as NoteType.Priority).subTasks ?: emptyList(),
            onCancel = {
                viewModel.onBottomSheetCancel()
            }
        ) {
            viewModel.onPrioritySubtasksChanged(it)
        }
    }
}

val constraints = ConstraintSet {
    val back = createRefFor("back")
    val title = createRefFor("label")
    val done = createRefFor("done")
    val titleEditText = createRefFor("titleEditText")
    val bodyEditText = createRefFor("bodyEditText")
    val tags = createRefFor("tags")
    val snackBar = createRefFor("snackbar")
    val time = createRefFor("time")
    val tasksCount = createRefFor("tasksCount")

    val guideline = createGuidelineFromTop(0.7f)

    constrain(back) {
        start.linkTo(parent.start)
        top.linkTo(title.top)
        bottom.linkTo(title.bottom)
    }
    constrain(title) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(done) {
        top.linkTo(title.top)
        end.linkTo(parent.end)
        bottom.linkTo(title.bottom)
    }
    constrain(titleEditText) {
        top.linkTo(title.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(bodyEditText) {
        top.linkTo(titleEditText.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(guideline)
        height = Dimension.fillToConstraints
    }
    constrain(tags) {
        top.linkTo(guideline)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(time) {
        top.linkTo(tags.bottom)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
    }
    constrain(snackBar) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(tasksCount) {
        top.linkTo(tags.bottom)
        start.linkTo(parent.start)
    }
}
