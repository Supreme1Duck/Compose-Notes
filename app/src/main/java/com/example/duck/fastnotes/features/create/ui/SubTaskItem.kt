package com.example.duck.fastnotes.features.create.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BasePurple
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.utils.ViewUtils.matchConstraintsSize

@Preview
@Composable
fun SubTaskItem(text: String = "Discussion for concept UI", isDone: Boolean = true, onClick: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        border = if (isDone) BorderStroke(1.5.dp, BasePurple) else null,
        colors = CardDefaults.cardColors(containerColor = MainTheme.colors.secondary)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = MainTheme.spacing.default), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                modifier = Modifier.wrapContentHeight(CenterVertically),
                text = text,
                textDecoration = if (isDone) TextDecoration.LineThrough else null,
                style = MainTheme.typography.h2.copy(fontSize = 16.sp, fontWeight = FontWeight.W300)
            )

            if (isDone) {
                Icon(
                    modifier = Modifier.height(24.dp),
                    painter = painterResource(id = R.drawable.subtask_icon_checked),
                    contentDescription = null,
                    tint = BasePurple
                )
            }
        }
    }
}