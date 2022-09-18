package com.mk.tasky.agenda.presentation.home

import java.time.LocalDateTime

data class AgendaItem(
    val id: String,
    val title: String,
    val isDone: Boolean,
    val description: String,
    val type: HomeAgendaType,
    val firstDatetime: LocalDateTime,
    val secondDatetime: LocalDateTime? = null
)
