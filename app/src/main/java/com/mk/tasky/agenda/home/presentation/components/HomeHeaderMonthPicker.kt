package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.White
import java.time.LocalDateTime

@Composable
fun HomeHeaderMonthPicker(
    localDateTime: LocalDateTime,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = localDateTime.month.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = White
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            tint = White,
            contentDescription = "change month"
        )
    }
}

@Preview
@Composable
fun HomeHeaderMonthPickerPreview() {
    HomeHeaderMonthPicker(LocalDateTime.now())
}
