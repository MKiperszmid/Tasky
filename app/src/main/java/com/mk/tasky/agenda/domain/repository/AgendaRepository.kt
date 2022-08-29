package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Reminder

interface AgendaRepository {
    suspend fun insertReminder(reminder: Reminder)
}
