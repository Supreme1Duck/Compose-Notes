package com.example.duck.fastnotes.features.dashboard.personal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.BasePurple
import com.example.duck.fastnotes.ui.theme.FastNotesTypography
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.utils.Dimens

@Preview
@Composable
fun ProfileScreen(viewModel: PersonalViewModel = viewModel()) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MainTheme.spacing.default)
        .padding(top = MainTheme.spacing.small, bottom = MainTheme.spacing.bottom),
        verticalArrangement = Arrangement.SpaceBetween) {

        Row {
            androidx.compose.material.Icon(
                Icons.Outlined.Person,
                contentDescription = stringResource(id = R.string.dashboard_app_bar_profile),
                Modifier
                    .padding(start = Dimens.DEFAULT_MARGIN)
                    .align(Alignment.CenterVertically)
            )
            androidx.compose.material.Text(
                text = "Иван Канаш",
                Modifier.padding(start = Dimens.DEFAULT_MARGIN),
                style = FastNotesTypography.h3
            )
        }

        AboutPerson()

        Column {
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = { },
                border = BorderStroke(1.dp, BasePurple)) {
                Text(text = "Сменить пароль",
                    color = Color.Black,
                    style = MainTheme.typography.subtitle1,
                    fontSize = 14.sp)
            }

            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = { },
                border = BorderStroke(1.dp, BasePurple)) {
                Text(text = "Настройки",
                    color = Color.Black,
                    style = MainTheme.typography.subtitle1,
                    fontSize = 14.sp)
            }

            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = { },
                border = BorderStroke(1.dp, BasePurple)) {
                Text(text = "Пользовательское соглашение",
                    color = Color.Black,
                    style = MainTheme.typography.subtitle1,
                    fontSize = 14.sp)
            }

            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = { },
                border = BorderStroke(1.dp, BasePurple)) {
                Text(text = "О разработчике",
                    color = Color.Black,
                    style = MainTheme.typography.subtitle1,
                    fontSize = 14.sp)
            }
        }

        OutlinedButton(modifier = Modifier.fillMaxWidth(),
            onClick = { },
            border = BorderStroke(1.dp, BasePurple)) {
            Text(text = "Выйти", color = Color.Black)
        }
    }
}

@Composable
fun AboutPerson() {

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
        Card(modifier = Modifier.size(80.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
        ) {
            Icon(
                Icons.Outlined.Person,
                tint = Color.White,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(MainTheme.spacing.smaller)
                    .size(50.dp)
                    .padding(top = 10.dp),
                contentDescription = stringResource(id = R.string.dashboard_app_bar_profile),
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            text = "ivanbudko@mail.ru",
            textAlign = TextAlign.Center,
            style = MainTheme.typography.h3.copy(fontSize = 16.sp)
        )
    }
}