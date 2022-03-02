package com.example.duck.fastnotes.features.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.features.create.BasicTypes.basicTypes
import com.example.duck.fastnotes.features.create.BasicTypes.getBasicNotes
import com.example.duck.fastnotes.features.dashboard.HomeScreens
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CreateTaskScreen(navController: NavController? = null) {

    var titleInput by rememberSaveable { mutableStateOf("") }
    var bodyInput by rememberSaveable { mutableStateOf("") }
    var menuState by remember { mutableStateOf(false) }
    var color: ColorTypeWrapper? by rememberSaveable { mutableStateOf(ColorTypeWrapper(Color.Transparent)) }

    var isColorSelected = false

    val constraints = ConstraintSet {
        val back = createRefFor("back")
        val title = createRefFor("title")
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
                    navController?.navigate(HomeScreens.Main.route) {
                        popUpTo(HomeScreens.Main.route)
                    }
                }
        )

        Text(
            text = stringResource(id = R.string.create_screen_title), modifier = Modifier
                .layoutId("title")
                .fillMaxWidth()
                .padding(all = Dimens.DEFAULT_MARGIN),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )

        TextField(
            value = titleInput,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_title)) },
            onValueChange = {
                titleInput = it
            },
            singleLine = true,
            modifier = Modifier
                .layoutId("titleEditText")
                .fillMaxWidth()
                .padding(vertical = Dimens.DEFAULT_MARGIN)
        )

        TextField(
            value = bodyInput,
            placeholder = { Text(text = stringResource(id = R.string.create_screen_hint_body)) },
            onValueChange = {
                bodyInput = it
            },
            modifier = Modifier
                .layoutId("bodyEditText")
                .fillMaxWidth()
        )

        FlowRow(
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("tags")
                .padding(top = Dimens.SMALL_MARGIN),
            lastLineMainAxisAlignment = FlowMainAxisAlignment.Center
        ) {
            getBasicNotes().forEach {
                NoteTypeItem(name = it.name) {
                    if (!isColorSelected)
                        color = basicTypes[it.name]
                }
            }
        }

        Box(Modifier
            .layoutId("colors")
            .padding(top = Dimens.LARGE_MARGIN)
            .clickable {
                menuState = true
            }
            .shadow(7.dp)) {

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(30.dp)
                    .background(Color.White)
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Row {
                    Box(
                        Modifier
                            .background(color?.value ?: Color.Unspecified)
                            .weight(4f)
                            .height(15.dp)
                    )

                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "",
                        Modifier
                            .weight(1f)
                            .height(15.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = menuState,
                onDismissRequest = { menuState = false }
            ) {
                basicTypes.forEach {
                    ColorTypeItem(color = it.value.value) {
                        isColorSelected = true
                        color = ColorTypeWrapper(it.value.value)
                        menuState = false
                    }
                }
            }
        }
    }
}