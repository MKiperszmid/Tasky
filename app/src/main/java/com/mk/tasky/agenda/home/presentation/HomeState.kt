package com.mk.tasky.agenda.home.presentation

import java.time.LocalDateTime

data class HomeState(
    val currentDate: LocalDateTime = LocalDateTime.now(),
    val selectedDay: Int = 0,
    val profileName: String = "",
    val agendaItems: List<String> = emptyList() // TODO: Make it Agenda items
)
