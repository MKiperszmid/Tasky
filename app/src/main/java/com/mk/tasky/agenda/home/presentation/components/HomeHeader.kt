package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mk.tasky.core.presentation.TaskyHeader
import java.time.LocalDateTime

@Composable
fun HomeHeader(
    date: LocalDateTime,
    name: String,
    onMonthClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TaskyHeader(
        modifier = modifier
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
