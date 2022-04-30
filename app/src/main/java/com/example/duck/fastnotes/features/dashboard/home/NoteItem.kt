package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.data.TaskItem
import com.example.duck.fastnotes.features.create.NoteType
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.utils.Dimens

@ExperimentalMaterialApi
@Composable
fun NoteItem(item: TaskItem) {

    val noteType = when (item.type) {
        NoteType.Personal.label -> NoteType.Personal
        NoteType.Health.label -> NoteType.Health
        NoteType.Work.label -> NoteType.Work
        NoteType.Entertainment.label -> NoteType.Entertainment
        NoteType.Education.label -> NoteType.Education
        NoteType.Shopping.label -> NoteType.Shopping
        NoteType.Sport.label -> NoteType.Sport
        else -> throw IllegalArgumentException()
    }

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(Dimens.SMALLER_MARGIN),
        backgroundColor = noteType.color.value,
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = {}
    ) {
        Column {
            Icon(
                painterResource(id = noteType.iconResource),
                contentDescription = stringResource(id = R.string.dashboard_note_action_image),
                modifier = Modifier.padding(Dimens.DEFAULT_MARGIN).size(27.dp)
            )

            Spacer(Modifier.height(Dimens.LARGE_MARGIN))

            Text(
                text = item.name,
                style = FastNotesTypography.h4,
                modifier = Modifier.padding(start = Dimens.DEFAULT_MARGIN)
            )

            Spacer(modifier = Modifier.height(Dimens.SMALL_MARGIN))

            Text(
                text = item.body,
                style = FastNotesTypography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = Dimens.DEFAULT_MARGIN)
            )

            if (item.body.isNotBlank())
                Spacer(modifier = Modifier.height(Dimens.DEFAULT_MARGIN))
        }
    }
}