package com.mk.tasky.agenda.domain.usecase.reminder

import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class SaveReminder(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(
        id: String?,
        title: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        reminder: NotificationTypes
    ) {
        val reminderTime = LocalDateTime.of(date, time)
        val remindAtTime = NotificationTypes.remindAt(reminderTime, reminder)
        val isEdit = id != null
        val agendaReminder = AgendaItem.Reminder(
            reminderId = id ?: UUID.randomUUID().toString(),
            reminderTitle = title,
            reminderDescription = description,
            reminderDateTime = reminderTime,
            reminderRemindAt = remindAtTime
        )
        repository.insertReminder(agendaReminder, isEdit)
    }
}
