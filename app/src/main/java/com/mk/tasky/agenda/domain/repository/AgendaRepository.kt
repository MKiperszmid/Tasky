package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Reminder
import java.time.LocalDate

interface AgendaRepository {
    suspend fun insertReminder(reminder: Reminder, isEdit: Boolean): Result<Unit>
    suspend fun getReminderById(id: String): Reminder
    suspend fun getRemindersForDate(date: LocalDate): List<Reminder>
    suspend fun deleteReminderById(id: String): Result<Unit>
}
