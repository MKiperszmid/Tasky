package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {
    suspend fun insertReminder(reminder: Reminder)
    fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>>
}
