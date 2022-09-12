package com.mk.tasky.agenda.domain.usecase

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
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
    ): Result<Unit> {
        val reminderTime = LocalDateTime.of(date, time)
        val remindAtTime = when (
            reminder
        ) {
            NotificationTypes.TEN_MINUTES -> reminderTime.minusMinutes(10)
            NotificationTypes.THIRTY_MINUTES -> reminderTime.minusMinutes(30)
            NotificationTypes.ONE_HOUR -> reminderTime.minusHours(1)
            NotificationTypes.SIX_HOURS -> reminderTime.minusHours(6)
            NotificationTypes.ONE_DAY -> reminderTime.minusDays(1)
        }
        val isEdit = id != null
        val agendaReminder = Reminder(
            id = id ?: UUID.randomUUID().toString(),
            title = title,
            description = description,
            dateTime = reminderTime,
            remindAt = remindAtTime
        )

        return repository.insertReminder(agendaReminder, isEdit)
    }
}
