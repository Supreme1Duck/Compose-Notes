package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.ui.theme.PriorityTaskColor
import com.example.duck.fastnotes.utils.ViewUtils.matchConstraintsSize

@Preview
@Composable
fun BaseTask() {

    Card(
        modifier = Modifier
            .aspectRatio(1.5f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(PriorityTaskColor)
    ) {
        Column(modifier = Modifier
            .padding(MainTheme.spacing.default)
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            BaseContent()

            AdditionalInfo()
        }
    }
}

@Composable
fun BaseContent() {
    Column {
        PriorityType()

        Title(modifier = Modifier
            .padding(top = MainTheme.spacing.default)
            .fillMaxWidth(0.7f))
    }
}

@Composable
fun AdditionalInfo() {
    Column() {
        Divider(modifier = Modifier.padding(top = MainTheme.spacing.default), color = Color.White, thickness = 0.3.dp)

        Row(modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(top = MainTheme.spacing.default)) {

            Row() {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = MainTheme.spacing.default)
                        .matchConstraintsSize(),
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null,
                    tint = MainTheme.colors.primary
                )

                Text(
                    modifier = Modifier.padding(start = MainTheme.spacing.smaller),
                    text = "14 tasks",
                    color = Color.White,
                    style = MainTheme.typography.caption
                )
            }

            Row {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = MainTheme.spacing.default)
                        .matchConstraintsSize(),
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = MainTheme.colors.primary
                )

                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = MainTheme.spacing.smaller),
                    text = "7 done",
                    color = MainTheme.colors.primary,
                    style = MainTheme.typography.caption
                )
            }
        }
    }
}

@Composable
fun PriorityType(modifier: Modifier = Modifier) {

    Card(modifier = modifier
        .padding(vertical = MainTheme.spacing.smaller),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(Color.White))
    {
        Text(
            modifier = Modifier.padding(horizontal = MainTheme.spacing.default, vertical = MainTheme.spacing.small),
            text = "Priority",
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = MainTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {

    Text(
        modifier = modifier,
        text = "Redesign UI & Animate for Mobile App",
        color = Color.White,
        style = MainTheme.typography.h3.copy(fontSize = 20.sp),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}