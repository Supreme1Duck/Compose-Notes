package com.example.duck.fastnotes.features.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.ui.theme.*

@Preview
@Composable
fun NoteItem(num: Int = 0) {

    val colors = mapOf(
        0 to PersonalNoteColor,
        1 to WorkNoteColor,
        2 to HealthNoteColor,
        3 to RelaxNoteColor,
        4 to PersonalNoteColor
    )

    val names = mapOf<Int, String>(
        0 to "Информировать более торопливых читателей, которые может не будут целиком читать вашу заметку. Они смотрят только по первому абзацу и понимают, интересно им это или нет.",
        1 to "Прямо в заголовках дать ответы на вопросы",
        2 to "Взять плавки, полотенце и свежую одежду",
        3 to "На 19:00 на улице Победителей 79.",
        4 to "Собрать все документы, пересмотреть доклад о технологиях, выбрать костюм, обсудить планы с начальством по телефону."
    )

    val titles = mapOf<Int, String>(
        0 to "Рабочие встречи",
        1 to "Описать проблемы технологий",
        2 to "Сходить в бассейн",
        3 to "Заказать ужин в ресторане",
        4 to "Подготовиться к завтрашнему дню"
    )

    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = colors[num] ?: BasePurple)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = MainTheme.spacing.default)
                .padding(horizontal = MainTheme.spacing.small)
        ) {
            Text(text = titles[num] ?: "",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.W500)
            )

            Text(modifier = Modifier.padding(top = MainTheme.spacing.small),
                text = names[num] ?: "",
                color = Color.Black,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis)
        }
    }
}