package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AgendaRepository {
    suspend fun insertReminder(reminder: Reminder, isEdit: Boolean): Result<Unit>
    suspend fun getReminderById(id: String): Reminder
    suspend fun deleteReminderById(id: String): Result<Unit>
    fun getAgenda(date: LocalDateTime, forceRemote: Boolean): Flow<Agenda>
}
