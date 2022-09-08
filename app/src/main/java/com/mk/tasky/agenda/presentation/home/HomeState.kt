package com.mk.tasky.agenda.presentation.home

import com.mk.tasky.agenda.domain.model.Reminder
import java.time.LocalDateTime

data class HomeState(
    val currentDate: LocalDateTime = LocalDateTime.now(),
    val selectedDay: Int = 0,
    val profileName: String = "",
    val agendaItems: List<String> = emptyList(), // TODO: Make it Agenda items
    val showAgendaOptions: Boolean = false,
    val agendaTypes: List<HomeAgendaType> = listOf(
        HomeAgendaType.Event,
        HomeAgendaType.Task,
        HomeAgendaType.Reminder
    ),
    val selectedAgendaType: HomeAgendaType? = null,
    val reminders: List<Reminder> = emptyList(),
    val showItemOptions: Boolean = false,
    val selectedItemOptionId: String? = null,
    val isLoggedOut: Boolean = false,
    val showLogout: Boolean = false
)
