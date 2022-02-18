package com.example.duck.fastnotes.features.dashboard.today

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BlackColor
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.textSecondaryTitleStyle
import com.example.duck.fastnotes.utils.textTitleLightLarger
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import timber.log.Timber

@Preview
@Composable
fun TodayScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackColor)
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
                Modifier
                    .padding(end = Dimens.DEFAULT_MARGIN, top = Dimens.SMALLER_MARGIN)
                    .size(32.dp),
                tint = Color.White
            )

            Column {
                Text(
                    text = stringResource(id = R.string.today_screen_title),
                    style = textTitleLightLarger(),
                    modifier = Modifier.padding(start = Dimens.LARGER_MARGIN)
                )
                Text(
                    text = stringResource(id = R.string.today_screen_subtitle, 3),
                    style = textSecondaryTitleStyle(),
                    modifier = Modifier
                        .padding(vertical = Dimens.SMALL_MARGIN)
                        .padding(start = Dimens.LARGER_MARGIN)
                )
            }
        }

        val list = listOf(
            DrawLineItem("12:00", "13:00", Color.Red, 1),
            DrawLineItem("15:00", "17:00", Color.Yellow, 2),
            DrawLineItem("17:00", "19:00", Color.Blue, 2),
            DrawLineItem("20:00", "21:00", Color.Green, 2),
        )

        TimeLineView(list)
    }
}

@Composable
fun TimeLineView(list: List<DrawLineItem>) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical)
            .padding(
                top = Dimens.DEFAULT_MARGIN,
                bottom = Dimens.BOTTOM_BAR_SIZE,
                start = Dimens.DEFAULT_MARGIN
            )
    ) {

        val offsetX = 0f
        var offsetY = 0f

        val textOffsetX = 50f

        Timber.tag("AndrewDebug").d("$size")

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
                                color = android.graphics.Color.parseColor("#FFFFFF")
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
                    Timber
                        .tag("AndrewDebug")
                        .d("SameTimeExists")
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
                    Timber
                        .tag("AndrewDebug")
                        .d("OnePointHourBetween")
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
                    Timber
                        .tag("AndrewDebug")
                        .d("OneHourBetween")
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
                    Timber
                        .tag("AndrewDebug")
                        .d("MultiHour")
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
                    Timber
                        .tag("AndrewDebug")
                        .d("FreeTime")
                    drawDividerLine()
                }
            }
        } //End of forEach()
        Timber.tag("AndrewDebug").d("$size")
    }
}

enum class LineType {

    SameTimeExists,
    OneHourBetween,
    OnePointHourBetween,
    MultiHour,
    FreeTime,
}
