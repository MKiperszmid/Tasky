package com.mk.tasky.agenda.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun HomeDayPicker(
    date: LocalDate,
    selectedDay: Int = 0,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (i in 0..5) {
            HomeDayPickerItem(
                day = date.plusDays(i.toLong()),
                isSelected = selectedDay == i,
                onDayClick = {
                    onDayClick(i)
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeDayPickerPreview() {
    HomeDayPicker(
        date = LocalDate.now(),
        selectedDay = 3,
        onDayClick = {}
    )
}
