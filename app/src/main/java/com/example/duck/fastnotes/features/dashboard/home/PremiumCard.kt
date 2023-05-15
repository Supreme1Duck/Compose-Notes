package com.example.duck.fastnotes.features.dashboard.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.ui.theme.OnSecondaryColor
import com.example.duck.fastnotes.ui.theme.SecondaryDarkerColor
import com.example.duck.fastnotes.utils.Dimens


@Preview
@Composable
fun PremiumCard() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().height(Dimens.PremiumCardSize)) {
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
                    top.linkTo(parent.top, margin = Dimens.LARGER_MARGIN)
                    start.linkTo(parent.start, margin = Dimens.LARGER_MARGIN)
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
                    start.linkTo(imgStar.end, margin = Dimens.DEFAULT_MARGIN)
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
                    bottom.linkTo(parent.bottom, margin = Dimens.SMALL_MARGIN)
                    end.linkTo(parent.end, margin = Dimens.SMALL_MARGIN)
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