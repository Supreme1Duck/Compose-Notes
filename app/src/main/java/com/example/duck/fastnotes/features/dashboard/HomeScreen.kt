package com.example.duck.fastnotes.features.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Favorite
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
import com.example.duck.fastnotes.ui.theme.BackgroundColor
import com.example.duck.fastnotes.ui.theme.SecondaryColor
import com.example.duck.fastnotes.utils.Dimens


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
                .height(150.dp)
                .padding(horizontal = Dimens.DEFAULT_MARGIN)
                .padding(bottom = Dimens.LARGE_MARGIN),
            backgroundColor = Color.Black,
            shape = RoundedCornerShape(30.dp)
        ) {
            Row {
                Card(
                    backgroundColor = SecondaryColor,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(horizontal = Dimens.LARGE_MARGIN)
                        .padding(top = Dimens.LARGER_MARGIN),
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.dashboard_premium_icon),
                        modifier = Modifier.padding(Dimens.SMALLER_MARGIN))
                }
            }
        }

        LazyVerticalGrid(cells = GridCells.Fixed(2), Modifier.padding(top = Dimens.SMALL_MARGIN)) {
            item { NoteItem(title = "First item", description = "First Item description") }
            item { NoteItem(title = "Second item", description = "Second Item description") }
            item { NoteItem(title = "Third item", description = "Third item description") }
            item { NoteItem(title = "Fourth item", description = "Third item description") }
            item { NoteItem(title = "Fifth item", description = "Third item description") }
        }
    }
}