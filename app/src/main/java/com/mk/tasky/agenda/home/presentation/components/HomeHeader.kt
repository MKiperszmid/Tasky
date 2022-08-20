package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@Composable
fun HomeHeader(
    date: LocalDateTime,
    name: String,
    onMonthClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeHeaderMonthPicker(
            date = date,
            onMonthClick = onMonthClick
        )
        HomeHeaderProfileName(name = name, onProfileClick = onProfileClick)
    }
}

@Preview
@Composable
fun HomeHeaderPreview() {
    HomeHeader(date = LocalDateTime.now(), name = "MK", {}, {})
}
