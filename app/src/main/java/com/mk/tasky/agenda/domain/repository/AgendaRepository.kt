package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {
    suspend fun insertReminder(reminder: AgendaItem.Reminder, isEdit: Boolean)
    suspend fun getReminderById(id: String): AgendaItem.Reminder
    suspend fun deleteReminderById(id: String)
    fun getAgenda(date: LocalDate, forceRemote: Boolean): Flow<Agenda>

    suspend fun insertTask(task: AgendaItem.Task, isEdit: Boolean)
    suspend fun changeStatusTask(id: String, isDone: Boolean)
    suspend fun getTaskById(id: String): AgendaItem.Task
    suspend fun deleteTaskById(id: String)
}
