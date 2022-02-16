package com.example.duck.fastnotes.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.ui.theme.PrimaryColor
import com.example.duck.fastnotes.ui.theme.SecondaryColor
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.TextDescDefault
import com.example.duck.fastnotes.utils.TextTitleSmall

@ExperimentalMaterialApi
@Composable
fun NoteItem(title: String, description: String) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(150.dp),
        backgroundColor = SecondaryColor,
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        onClick = {}
    ) {
        Column {
            
            TextTitleSmall(text = title, Dimens.SMALLER_MARGIN)

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .padding(start = 5.dp, end = 20.dp)
                    .background(PrimaryColor)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextDescDefault(text = description)
            
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}