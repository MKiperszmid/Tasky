package com.mk.tasky.agenda.detail.reminder.domain.usecase

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SaveReminder(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        reminder: ReminderTypes
    ) {
        val reminderTime = LocalDateTime.of(date, time)
        val remindAtTime = when (
            reminder
        ) {
            ReminderTypes.TEN_MINUTES -> reminderTime.minusMinutes(10)
            ReminderTypes.THIRTY_MINUTES -> reminderTime.minusMinutes(30)
            ReminderTypes.ONE_HOUR -> reminderTime.minusHours(1)
            ReminderTypes.SIX_HOURS -> reminderTime.minusHours(6)
            ReminderTypes.ONE_DAY -> reminderTime.minusDays(1)
        }
        val reminder = Reminder(
            title = title,
            description = description,
            time = reminderTime,
            remindAt = remindAtTime
        )

        repository.insertReminder(reminder)
    }
}
