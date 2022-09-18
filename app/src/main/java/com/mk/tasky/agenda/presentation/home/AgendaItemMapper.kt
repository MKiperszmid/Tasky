package com.mk.tasky.agenda.presentation.home

import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.model.Task

// TODO: Add Event
fun Agenda.toAgendaItem(): List<AgendaItem> {
    return this.reminders.map { it.toAgendaItem() } + this.tasks.map { it.toAgendaItem() }
}

fun Reminder.toAgendaItem(): AgendaItem {
    return AgendaItem(
        id = this.id,
        title = this.title,
        type = HomeAgendaType.Reminder,
        description = this.description,
        firstDatetime = this.dateTime,
        isDone = false
    )
}

fun Task.toAgendaItem(): AgendaItem {
    return AgendaItem(
        id = this.id,
        title = this.title,
        type = HomeAgendaType.Task,
        description = this.description,
        firstDatetime = this.dateTime,
        isDone = this.isDone
    )
}
