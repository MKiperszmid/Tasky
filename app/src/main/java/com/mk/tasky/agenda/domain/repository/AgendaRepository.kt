package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AgendaRepository {
    suspend fun insertReminder(reminder: Reminder, isEdit: Boolean)
    suspend fun getReminderById(id: String): Reminder
    suspend fun deleteReminderById(id: String)
    fun getAgenda(date: LocalDateTime, forceRemote: Boolean): Flow<Agenda>

    suspend fun insertTask(task: Task, isEdit: Boolean)
    suspend fun changeStatusTask(id: String, isDone: Boolean)
    suspend fun getTaskById(id: String): Task
    suspend fun deleteTaskById(id: String)
}
