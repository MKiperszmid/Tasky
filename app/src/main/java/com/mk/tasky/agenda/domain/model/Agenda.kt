package com.mk.tasky.agenda.domain.model

data class Agenda(
    val reminders: List<Reminder>,
    val tasks: List<Task>,
    val events: List<Event>
)
