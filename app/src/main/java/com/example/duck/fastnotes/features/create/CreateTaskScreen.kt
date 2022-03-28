package com.example.duck.fastnotes.features.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.create.BasicTypes.getBasicNotes
import com.example.duck.fastnotes.features.dashboard.home.ToggleGroup
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateTaskScreen(
    navController: NavController,
    viewModel: CreateTaskViewModel = hiltViewModel()
) {
    rememberSaveable { viewModel.title }
    rememberSaveable { viewModel.body }
    rememberSaveable { viewModel.canDone }
    rememberSaveable { viewModel.noteType }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                CreateTaskViewModel.UIState.SaveNote -> navController.navigateUp()

                is CreateTaskViewModel.UIState.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
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
                    navController.navigateUp()
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
                .noRippleClickable { viewModel.onEvent(CreateScreenContract.OnSaveItem) }
                .alpha(if (!viewModel.canDone) 0.5f else 1f)
        )

        TextField(
            value = viewModel.title,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_title)) },
            onValueChange = { viewModel.onEvent(CreateScreenContract.OnEnteredContent(it)) },
            singleLine = true,
            modifier = Modifier
                .layoutId("titleEditText")
                .fillMaxWidth()
                .padding(vertical = Dimens.DEFAULT_MARGIN)
        )

        TextField(
            value = viewModel.body,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_body)) },
            onValueChange = { viewModel.onEvent(CreateScreenContract.OnEnteredBody(it)) },
            modifier = Modifier
                .layoutId("bodyEditText")
                .fillMaxWidth()
        )

        ToggleGroup(
            options = getBasicNotes().map { it.label to it.color.value },
            selectedOption = viewModel.noteType.value?.label,
            modifier = Modifier.layoutId("tags")
        ) { label ->
            viewModel.onEvent(CreateScreenContract.OnTypeChanged(label))
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
    val colors = createRefFor("colors")

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
    constrain(colors) {
        top.linkTo(tags.bottom)
        start.linkTo(parent.start)
    }
}