package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.data.NoteItem
import com.example.duck.fastnotes.ui.WelcomeScreenSpacingComposition
import com.example.duck.fastnotes.ui.theme.*
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.Dimens.DEFAULT_MARGIN
import com.example.duck.fastnotes.utils.Dimens.LARGER_MARGIN
import com.example.duck.fastnotes.utils.Dimens.PremiumCardSize
import com.example.duck.fastnotes.utils.Dimens.SMALL_MARGIN

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
            text = stringResource(id = R.string.dashboard_app_bar_title, userName),
            Modifier.padding(start = DEFAULT_MARGIN),
            style = FastNotesTypography.h3
        )
    }
}

@Preview
@Composable
fun PremiumCardDev() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().height(PremiumCardSize)) {
        val (imgStar, title, description, btnNext) = createRefs()

        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = BlackColor,
            shape = RoundedCornerShape(30.dp),
            content = {}
        )

        Card(
            backgroundColor = SecondaryDarkerColor,
            shape = CircleShape,
            modifier = Modifier
                .constrainAs(imgStar) {
                    top.linkTo(parent.top, margin = LARGER_MARGIN)
                    start.linkTo(parent.start, margin = LARGER_MARGIN)
                },
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                tint = Color.White,
                contentDescription = stringResource(id = R.string.dashboard_premium_icon),
                modifier = Modifier
                    .padding(Dimens.SMALLER_MARGIN)
                    .size(20.dp, 20.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.dashboard_premium_title),
            style = FastNotesTypography.h3.copy(color = Color.White),
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(title) {
                    top.linkTo(imgStar.top)
                    bottom.linkTo(imgStar.bottom)
                    start.linkTo(imgStar.end, margin = DEFAULT_MARGIN)
                }
        )

        Text(
            text = stringResource(id = R.string.dashboard_premium_description),
            style = FastNotesTypography.h5.copy(color = SecondaryDarkerColor),
            modifier = Modifier
                .width(200.dp)
                .constrainAs(description) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                }
        )

        Card(
            backgroundColor = OnSecondaryColor,
            shape = CircleShape,
            modifier = Modifier
                .clickable {}
                .constrainAs(btnNext) {
                    bottom.linkTo(parent.bottom, margin = SMALL_MARGIN)
                    end.linkTo(parent.end, margin = SMALL_MARGIN)
                }
        ) {
            Icon(
                Icons.Filled.ArrowForward,
                tint = Color.White,
                contentDescription = stringResource(id = R.string.dashboard_premium_title),
                modifier = Modifier.padding(Dimens.SMALLER_MARGIN)
            )
        }
    }
}

@Preview
@Composable
fun PremiumCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = BlackColor,
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Card(
                    backgroundColor = SecondaryDarkerColor,
                    shape = CircleShape,
                    modifier = Modifier.padding(top = Dimens.LARGER_MARGIN),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.dashboard_premium_icon),
                        modifier = Modifier
                            .padding(Dimens.SMALLER_MARGIN)
                            .size(20.dp, 20.dp)
                    )
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.dashboard_premium_title),
                        style = FastNotesTypography.h3.copy(color = Color.White),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = Dimens.LARGE_MARGIN)
                    )
                    Text(
                        text = stringResource(id = R.string.dashboard_premium_description),
                        style = FastNotesTypography.h5.copy(color = SecondaryDarkerColor),
                        modifier = Modifier
                            .width(200.dp)
                            .padding(top = Dimens.SMALL_MARGIN)
                    )
                }
            }
            Card(
                backgroundColor = OnSecondaryColor,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = Dimens.SMALL_MARGIN)
                    .padding(bottom = Dimens.SMALL_MARGIN)
                    .clickable {}
            ) {
                Icon(
                    Icons.Filled.ArrowForward,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.dashboard_premium_title),
                    modifier = Modifier
                        .padding(Dimens.SMALLER_MARGIN)
                )
            }
        }
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
