package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.ui.theme.OnSecondaryColor
import com.example.duck.fastnotes.ui.theme.SecondaryDarkerColor
import com.example.duck.fastnotes.utils.Dimens

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    name: String = " ",
    onNoteClick: (id: Int) -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()

    val list = viewModel.tasksList

    Column(Modifier.fillMaxSize().padding(horizontal = Dimens.DEFAULT_MARGIN)) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.LARGE_MARGIN, top = Dimens.SMALL_MARGIN)
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = stringResource(id = R.string.dashboard_app_bar_profile),
                Modifier
                    .padding(start = Dimens.DEFAULT_MARGIN)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = stringResource(id = R.string.dashboard_app_bar_title, name),
                Modifier.padding(start = Dimens.DEFAULT_MARGIN),
                style = FastNotesTypography.h3
            )
        }

        Card(
            Modifier
                .fillMaxWidth()
                .height(165.dp)
                .padding(bottom = Dimens.LARGE_MARGIN),
            backgroundColor = BlackColor,
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Card(
                    backgroundColor = SecondaryDarkerColor,
                    shape = CircleShape,
                    modifier = Modifier.padding(top = Dimens.LARGER_MARGIN),
                ) {
                    Icon(
                        Icons.Filled.Star,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.dashboard_premium_icon),
                        modifier = Modifier
                            .padding(Dimens.SMALLER_MARGIN)
                            .size(20.dp, 20.dp)
                    )
                }
                Column{
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
                Card(
                    backgroundColor = OnSecondaryColor,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.Bottom)
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

        Text(
            text = stringResource(id = R.string.dashboard_title_tasks),
            style = FastNotesTypography.subtitle1,
            modifier = Modifier.padding(
                start = Dimens.DEFAULT_MARGIN,
                bottom = Dimens.SMALLER_MARGIN
            )
        )

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = Dimens.SMALLER_MARGIN),
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = Dimens.BOTTOM_BAR_SIZE)
        ) {
            list.value.forEach {
                item { NoteItem(item = it, onClick = onNoteClick) }
            }
        }
    }
}
