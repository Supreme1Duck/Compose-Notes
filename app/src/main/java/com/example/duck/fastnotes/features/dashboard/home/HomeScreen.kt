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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.TaskItem
import com.example.duck.fastnotes.features.dashboard.NoteItem
import com.example.duck.fastnotes.ui.theme.*
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.TextSecondaryTitle
import com.example.duck.fastnotes.utils.textDefaultDarkerStyleLarge
import com.example.duck.fastnotes.utils.textDefaultTitleStyle
import timber.log.Timber


@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController, name: String) {

    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.LARGE_MARGIN)
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
                style = TextStyle(
                    fontSize = Dimens.TEXT_TITLE, fontWeight = FontWeight.SemiBold
                )
            )
        }

        Card(
            Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = Dimens.DEFAULT_MARGIN)
                .padding(bottom = Dimens.LARGE_MARGIN),
            backgroundColor = BlackColor,
            shape = RoundedCornerShape(30.dp)
        ) {
            Row {
                Card(
                    backgroundColor = SecondaryDarkerColor,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(horizontal = Dimens.LARGE_MARGIN)
                        .padding(top = Dimens.LARGER_MARGIN),
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
                Column {
                    Text(
                        text = stringResource(id = R.string.dashboard_premium_title),
                        style = textDefaultTitleStyle(),
                        modifier = Modifier
                            .padding(top = Dimens.LARGE_MARGIN)
                    )
                    Text(
                        text = stringResource(id = R.string.dashboard_premium_description),
                        style = textDefaultDarkerStyleLarge(),
                        modifier = Modifier
                            .padding(top = Dimens.SMALL_MARGIN)
                            .width(240.dp)
                    )
                }
                Card(
                    backgroundColor = OnSecondaryColor,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = Dimens.SMALL_MARGIN, bottom = Dimens.SMALL_MARGIN)
                        .clickable {
                            Timber
                                .tag("AndrewDebug")
                                .d("Arrow Forward clicked")
                        }
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

        TextSecondaryTitle(text = stringResource(id = R.string.dashboard_title_tasks))

        LazyVerticalGrid(cells = GridCells.Fixed(2), contentPadding = PaddingValues(bottom = Dimens.BOTTOM_BAR_SIZE)) {
            mockNoteObject().forEach {
                item { NoteItem(item = it) }
            }
        }

    }
}

fun mockNoteObject(): List<TaskItem> {
    return listOf(
//        TaskItem("Personal", imageSrc = Icons.Filled.Person, PersonalNoteColor, 0, 3),
//        TaskItem("Work", imageSrc = Icons.Filled.Place, WorkNoteColor, 1, 0),
//        TaskItem("Health", imageSrc = Icons.Filled.Favorite, HealthNoteColor, 3, 0),
//        TaskItem("Health", imageSrc = Icons.Filled.Favorite, HealthNoteColor, 3, 0),
//        TaskItem("Health", imageSrc = Icons.Filled.Favorite, HealthNoteColor, 3, 0)
    )
}