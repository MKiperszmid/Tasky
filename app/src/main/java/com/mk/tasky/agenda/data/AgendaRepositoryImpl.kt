package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.data.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AgendaRepositoryImpl(
    private val dao: AgendaDao,
    private val api: AgendaApi
) : AgendaRepository {
    // TODO: Have the functions receive AgendaItem, and based on the item call the API function, as to avoid duplicated functions
    override suspend fun insertReminder(reminder: AgendaItem.Reminder, isEdit: Boolean) {
        dao.insertReminder(reminder.toEntity())
        saveReminderRemotely(reminder, isEdit).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun saveReminderRemotely(reminder: AgendaItem.Reminder, isEdit: Boolean) =
        resultOf {
            reminder.toDto().let {
                if (isEdit) {
                    api.updateReminder(it)
                } else {
                    api.createReminder(it)
                }
            }
        }

    override suspend fun getReminderById(id: String): AgendaItem.Reminder {
        return dao.getReminderById(id).toDomain()
    }

    private suspend fun getRemindersForDate(date: LocalDate): List<AgendaItem.Reminder> {
        val dayOne = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val dayTwo = date.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()

        return dao.getRemindersBetweenTimestamps(
            dayOne = dayOne,
            dayTwo = dayTwo
        ).map {
            it.toDomain()
        }
    }

    override suspend fun deleteReminderById(id: String) {
        dao.deleteReminderById(id)
        deleteReminderByIdRemotely(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun deleteReminderByIdRemotely(id: String) = resultOf {
        api.deleteReminder(id)
    }

    override fun getAgenda(date: LocalDate, forceRemote: Boolean): Flow<Agenda> {
        // TODO: Update with Tasks and Events
        return flow {
            val localReminders = getRemindersForDate(date)
            val localTasks = getTasksForDate(date)
            emit(Agenda(localReminders + localTasks))
            if (forceRemote) {
                getAgendaRemotely(date).onSuccess { response ->
                    supervisorScope {
                        val reminders = response.reminders.map {
                            launch { dao.insertReminder(it.toDomain().toEntity()) }
                        }
                        val tasks = response.tasks.map {
                            launch { dao.insertTask(it.toDomain().toEntity()) }
                        }
                        (reminders + tasks).forEach { it.join() }
                    }

                    val updatedLocalReminders = getRemindersForDate(date)
                    val updatedLocalTasks = getTasksForDate(date)
                    emit(Agenda(updatedLocalReminders + updatedLocalTasks))
                }.onFailure {
                    // TODO: Internet error
                    println("")
                }
            }
        }
    }

    private suspend fun getAgendaRemotely(date: LocalDate) = resultOf {
        val dateTime = LocalDateTime.of(date, LocalTime.now())
        api.getAgenda(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }

    override suspend fun insertTask(task: AgendaItem.Task, isEdit: Boolean) {
        dao.insertTask(task.toEntity())
        saveTaskRemotely(task = task, isEdit = isEdit).onFailure {
            // TODO: Save id on db to later sync with server
            println("")
        }
    }

    override suspend fun changeStatusTask(id: String, isDone: Boolean) {
        val task = getTaskById(id).copy(
            isDone = isDone
        )

        insertTask(task, isEdit = true)
    }

    private suspend fun saveTaskRemotely(task: AgendaItem.Task, isEdit: Boolean) = resultOf {
        task.toDto().let {
            if (isEdit) {
                api.updateTask(it)
            } else {
                api.createTask(it)
            }
        }
    }

    override suspend fun getTaskById(id: String): AgendaItem.Task {
        return dao.getTaskById(id).toDomain()
    }

    private suspend fun getTasksForDate(date: LocalDate): List<AgendaItem.Task> {
        val dayOne = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val dayTwo = date.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()

        return dao.getTasksBetweenTimestamps(
            dayOne = dayOne,
            dayTwo = dayTwo
        ).map {
            it.toDomain()
        }
    }

    override suspend fun deleteTaskById(id: String) {
        dao.deleteTaskById(id)
        deleteTaskByIdRemotely(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun deleteTaskByIdRemotely(id: String) = resultOf {
        api.deleteTask(id)
    }
}
