package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Orange
import java.time.LocalDateTime

@Composable
fun HomeDayPickerItem(
    day: LocalDateTime,
    isSelected: Boolean = false,
    onDayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(100))
            .background(if (isSelected) Orange else Color.Transparent).clickable {
                onDayClick()
            }.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayOfWeek.name[0].toString(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Black else Gray
        )
        Text(
            text = day.dayOfMonth.toString(),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

@Preview
@Composable
fun HomeDayPickerItemPreview() {
    HomeDayPickerItem(
        day = LocalDateTime.now(),
        isSelected = true,
        onDayClick = {}
    )
}
