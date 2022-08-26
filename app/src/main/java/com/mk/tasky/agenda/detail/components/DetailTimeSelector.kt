package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.ui.theme.Black
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailTimeSelector(
    text: String,
    date: LocalDate,
    time: LocalTime,
    isEditable: Boolean,
    onTimeClick: () -> Unit,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, fontSize = 16.sp, color = Black)
            Row(
                modifier = Modifier.then(
                    if (isEditable) {
                        Modifier.clickable {
                            onTimeClick()
                        }
                    } else Modifier
                ).padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val timeFormatted = time.format(timeFormatter)
                Text(
                    text = timeFormatted,
                    fontSize = 16.sp,
                    color = Black
                )
                if (isEditable) {
                    Icon(
                        imageVector = Icons.Default.NavigateNext,
                        contentDescription = stringResource(R.string.edit_time)
                    )
                }
            }
            val dateFormatter = DateTimeFormatter.ofPattern("MMM dd uuuu")
            val formatted = date.format(dateFormatter)
            Text(
                text = formatted,
                fontSize = 16.sp,
                color = Black,
                modifier = Modifier.then(
                    if (isEditable) {
                        Modifier.clickable {
                            onDateClick()
                        }
                    } else Modifier
                ).padding(start = 10.dp, end = 10.dp)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            if (isEditable) {
                Icon(
                    imageVector = Icons.Default.NavigateNext,
                    contentDescription = stringResource(R.string.edit_time)
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailTimeSelectorPreview() {
    DetailTimeSelector(
        text = "From",
        date = LocalDate.now(),
        time = LocalTime.now(),
        isEditable = true,
        onTimeClick = {},
        onDateClick = {}
    )
}
