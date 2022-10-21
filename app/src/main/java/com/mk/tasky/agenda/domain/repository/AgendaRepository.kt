package com.mk.tasky.agenda.domain.repository

import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.domain.model.SyncItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

// TODO: Create a repository for each item (ReminderRepository, TaskRepository, EventRepository)
interface AgendaRepository {
    suspend fun insertReminder(reminder: AgendaItem.Reminder, isEdit: Boolean)
    suspend fun getReminderById(id: String): AgendaItem.Reminder
    suspend fun deleteReminderById(id: String)
    fun getAgenda(date: LocalDate, forceRemote: Boolean): Flow<Agenda>

    suspend fun insertTask(task: AgendaItem.Task, isEdit: Boolean)
    suspend fun changeStatusTask(id: String, isDone: Boolean)
    suspend fun getTaskById(id: String): AgendaItem.Task
    suspend fun deleteTaskById(id: String)

    suspend fun getAttendee(email: String): Result<Attendee?>
    suspend fun getEventById(id: String): AgendaItem.Event
    suspend fun insertEvent(event: AgendaItem.Event, isEdit: Boolean)
    suspend fun deleteEventById(id: String)

    suspend fun getAllUpcomingItems(): Agenda

    suspend fun getAllSyncableItems(): List<SyncItem>
    suspend fun deleteSyncableItem(id: String)
    suspend fun saveSyncableItem(item: SyncItem)
}
