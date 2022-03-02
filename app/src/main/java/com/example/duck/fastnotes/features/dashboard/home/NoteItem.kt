package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.domain.TaskItem
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens

@ExperimentalMaterialApi
@Composable
fun NoteItem(item: TaskItem) {

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(Dimens.SMALLER_MARGIN),
        backgroundColor = item.color,
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = {}
    ) {

        Column {
            Icon(
                item.imageSrc,
                contentDescription = stringResource(id = R.string.dashboard_note_action_image),
                modifier = Modifier.padding(Dimens.DEFAULT_MARGIN)
            )

            Spacer(Modifier.height(Dimens.LARGE_MARGIN))

            Text(text = item.title, style = FastNotesTypography.h4, modifier = Modifier.padding(start = Dimens.DEFAULT_MARGIN))

            Spacer(modifier = Modifier.height(Dimens.SMALL_MARGIN))

            Row(Modifier.padding(bottom = Dimens.LARGE_MARGIN)) {

                Card(
                    modifier = Modifier
                        .padding(start = Dimens.DEFAULT_MARGIN)
                        .wrapContentWidth(),
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_note_action_estimate, item.estimate),
                        style = FastNotesTypography.caption,
                        modifier = Modifier.padding(Dimens.SMALL_MARGIN)
                    )
                }

                Card(
                    modifier = Modifier.padding(start = Dimens.DEFAULT_MARGIN),
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_note_action_done, item.inactive),
                        style = FastNotesTypography.caption,
                        modifier = Modifier.padding(Dimens.SMALL_MARGIN)
                    )
                }

            }

        }
    }

}