package com.example.duck.fastnotes.features.dashboard.today

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.ui.theme.PrimaryColor
import com.example.duck.fastnotes.utils.Dimens
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import timber.log.Timber

@Preview
@Composable
fun TodayScreen(list: List<Unit> = emptyList()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = Dimens.DEFAULT_MARGIN)
            .padding(start = Dimens.SMALLER_MARGIN)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.LARGE_MARGIN)
        ) {
            Icon(
                Icons.Outlined.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.today_screen_back),
                Modifier.padding(end = Dimens.DEFAULT_MARGIN, top = Dimens.SMALLER_MARGIN).size(32.dp),
                tint = Color.Black
            )

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.today_screen_title),
                    style = FastNotesTypography.h2,
                    color = Color.Black,
                    modifier = Modifier.padding(start = Dimens.LARGER_MARGIN),

                )
                Text(
                    text = stringResource(id = R.string.today_screen_subtitle, 3),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(vertical = Dimens.SMALL_MARGIN)
                        .padding(start = Dimens.LARGER_MARGIN)
                )
            }
        }

        val list1 = listOf(
            DrawLineItem("12:00", "13:00", Color.Red, 1),
            DrawLineItem("15:00", "17:00", Color.Yellow, 2),
            DrawLineItem("17:00", "19:00", Color.Blue, 2),
            DrawLineItem("20:00", "21:00", Color.Green, 2),
            DrawLineItem("20:00", "21:00", Color.Green, 2),
            DrawLineItem("20:00", "21:00", Color.Green, 2)
        )

        TimeLineView(list1)
    }
}

@Composable
fun TimeLineView(list: List<DrawLineItem>) {
    Canvas(
        modifier = Modifier
            .height(640.dp + Dimens.BOTTOM_BAR_SIZE)
            .padding(
                top = Dimens.DEFAULT_MARGIN,
                bottom = Dimens.BOTTOM_BAR_SIZE,
                start = Dimens.DEFAULT_MARGIN
            )
    ) {

        val offsetX = 0f
        var offsetY = 0f

        val textOffsetX = 50f

        val increaseValue = 200f

        list.forEachIndexed { index, item ->
            val formatter = DateTimeFormat.forPattern("H:mm")

            val timeStart = DateTime.parse(item.timeStart, formatter)
            val timeEnd = DateTime.parse(item.timeEnd, formatter)

            val nextTimeStart = if (list.getOrNull(index + 1) != null) DateTime.parse(
                list.getOrNull(index + 1)?.timeStart,
                formatter
            )
            else null

            val resultTime =
                if (nextTimeStart == null) Integer.MAX_VALUE
                else nextTimeStart.hourOfDay - timeEnd.hourOfDay

            val lineType =
                when {
                    resultTime == 0 -> LineType.SameTimeExists
                    resultTime == 2 -> LineType.OnePointHourBetween
                    resultTime > 2 -> LineType.MultiHour
                    resultTime == 1 -> LineType.OneHourBetween
                    else -> LineType.FreeTime
                }

            fun drawTimePoint() {
                drawCircle(
                    color = item.color,
                    radius = 15.dp.value,
                    center = Offset(offsetX, offsetY)
                )
            }

            fun drawTimeLine() {
                drawLine(
                    color = item.color,
                    start = Offset(offsetX, offsetY),
                    end = Offset(offsetX, offsetY + increaseValue),
                    strokeWidth = 5.dp.value
                )
                offsetY += increaseValue
            }

            fun drawDividerLine() {
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(offsetX, offsetY + 15.dp.value),
                    end = Offset(offsetX, offsetY + increaseValue),
                    strokeWidth = 5.dp.value
                )
                offsetY += increaseValue
            }

            fun drawDividerPoint() {
                drawCircle(
                    color = Color.DarkGray,
                    radius = 15.dp.value,
                    center = Offset(offsetX, offsetY)
                )
            }

            fun drawTextTime(text: String) {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        text,
                        textOffsetX,
                        offsetY + 15f,
                        android.graphics
                            .Paint()
                            .apply {
                                color = android.graphics.Color.parseColor("#000000")
                                textSize = 40F
                            }
                    )
                }
            }

            fun drawDisabledTextTime(text: String) {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        text,
                        textOffsetX,
                        offsetY + 15f,
                        android.graphics
                            .Paint()
                            .apply {
                                color = android.graphics.Color.parseColor("#979797")
                                textSize = 40F
                            }
                    )
                }
            }


            when (lineType) {
                LineType.SameTimeExists -> {
                    drawTimePoint()
                    drawTextTime(item.timeStart)
                    if (item.hours == 1) {
                        drawTimeLine()
                    }
                    repeat(item.hours - 1) { counter ->
                        drawTimeLine()
                        drawTimePoint()
                        drawTextTime(
                            timeStart
                                .plusHours(counter + 1)
                                .toString(formatter)
                        )
                        drawTimeLine()
                    }
                }
                LineType.OnePointHourBetween -> {
                    drawTimePoint()
                    drawTextTime(item.timeStart)
                    drawTimeLine()
                    repeat(item.hours - 2) { counter ->
                        drawTimePoint()
                        drawTextTime(
                            timeStart
                                .plusHours(counter + 2)
                                .toString(formatter)
                        )
                        drawTimeLine()
                    }
                    drawTimePoint()
                    drawTextTime(item.timeEnd)

                    drawDividerLine()
                    drawDividerPoint()
                    drawDisabledTextTime(
                        timeStart
                            .plusHours(item.hours + 1)
                            .toString(formatter)
                    )
                    drawDividerLine()
                }
                LineType.OneHourBetween -> {
                    drawTimePoint()
                    drawTextTime(item.timeStart)
                    repeat(item.hours) { counter ->
                        drawTimeLine()
                        drawTimePoint()
                        drawTextTime(
                            timeStart
                                .plusHours(counter + 1)
                                .toString(formatter)
                        )
                    }
                    drawDividerLine()
                }
                LineType.MultiHour -> {
                    drawTimePoint()
                    drawTextTime(item.timeStart)
                    repeat(item.hours - 1) { counter ->
                        drawTimeLine()
                        drawTimePoint()
                        drawTextTime(
                            timeStart
                                .plusHours(counter + 1)
                                .toString(formatter)
                        )
                    }
                    drawDividerLine()
                }
                LineType.FreeTime -> {
                    drawDividerLine()
                }
            }
        } //End of forEach()
    }
}

enum class LineType {

    SameTimeExists,
    OneHourBetween,
    OnePointHourBetween,
    MultiHour,
    FreeTime,
}
