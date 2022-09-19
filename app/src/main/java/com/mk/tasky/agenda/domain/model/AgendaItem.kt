package com.mk.tasky.agenda.domain.model

import java.time.LocalDateTime

sealed class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val firstDatetime: LocalDateTime,
    val remindAt: LocalDateTime
) {
    data class Reminder(
        val reminderId: String,
        val reminderTitle: String,
        val reminderDescription: String,
        val reminderDateTime: LocalDateTime,
        val reminderRemindAt: LocalDateTime
    ) : AgendaItem(
        id = reminderId,
        title = reminderTitle,
        description = reminderDescription,
        firstDatetime = reminderDateTime,
        remindAt = reminderRemindAt
    )

    data class Task(
        val taskId: String,
        val taskTitle: String,
        val taskDescription: String,
        val taskDateTime: LocalDateTime,
        val taskRemindAt: LocalDateTime,
        val isDone: Boolean
    ) : AgendaItem(
        id = taskId,
        title = taskTitle,
        description = taskDescription,
        firstDatetime = taskDateTime,
        remindAt = taskRemindAt
    )
}
