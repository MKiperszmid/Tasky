package com.mk.tasky.agenda.presentation.home

import com.mk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate
import java.time.LocalTime

data class HomeState(
    val currentDate: LocalDate = LocalDate.now(),
    val selectedDay: Int = 0,
    val profileName: String = "",
    val agendaItems: List<AgendaItem> = emptyList(),
    val showAgendaOptions: Boolean = false,
    val agendaTypes: List<HomeAgendaType> = listOf(
        HomeAgendaType.Event,
        HomeAgendaType.Task,
        HomeAgendaType.Reminder
    ),
    val selectedAgendaType: HomeAgendaType? = null,
    val showItemOptions: Boolean = false,
    val selectedAgendaItem: AgendaItem? = null,
    val isLoggedOut: Boolean = false,
    val showLogout: Boolean = false
)
