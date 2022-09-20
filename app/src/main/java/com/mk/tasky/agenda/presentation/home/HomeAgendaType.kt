package com.mk.tasky.agenda.presentation.home

import androidx.compose.ui.graphics.Color
import com.mk.tasky.ui.theme.Green
import com.mk.tasky.ui.theme.Light
import com.mk.tasky.ui.theme.LightGreen

sealed class HomeAgendaType(val name: String, val color: Color) {
    object Event : HomeAgendaType("Event", LightGreen)
    object Task : HomeAgendaType("Task", Green)
    object Reminder : HomeAgendaType("Reminder", Light)
}
