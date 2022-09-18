package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.model.Task
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.data.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class AgendaRepositoryImpl(
    private val dao: AgendaDao,
    private val api: AgendaApi
) : AgendaRepository {
    override suspend fun insertReminder(reminder: Reminder, isEdit: Boolean) {
        dao.insertReminder(reminder.toEntity())
        saveReminderRemotely(reminder, isEdit).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun saveReminderRemotely(reminder: Reminder, isEdit: Boolean) = resultOf {
        reminder.toDto().let {
            if (isEdit) {
                api.updateReminder(it)
            } else {
                api.createReminder(it)
            }
        }
    }

    override suspend fun getReminderById(id: String): Reminder {
        return dao.getReminderById(id).toDomain()
    }

    private suspend fun getRemindersForDate(date: LocalDate): List<Reminder> {
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

    override fun getAgenda(date: LocalDateTime, forceRemote: Boolean): Flow<Agenda> {
        // TODO: Update with Tasks and Events
        return flow {
            val localReminders = getRemindersForDate(date.toLocalDate())
            val localTasks = getTasksForDate(date.toLocalDate())
            emit(Agenda(reminders = localReminders, tasks = localTasks, events = emptyList()))
            if (forceRemote) {
                getAgendaRemotely(date).onSuccess { response ->
                    supervisorScope {
                        response.reminders.map {
                            launch { dao.insertReminder(it.toDomain().toEntity()) }
                        }.forEach { it.join() }
                        response.tasks.map {
                            launch { dao.insertTask(it.toDomain().toEntity()) }
                        }.forEach { it.join() }
                    }

                    val updatedLocalReminders = getRemindersForDate(date.toLocalDate())
                    val updatedLocalTasks = getTasksForDate(date.toLocalDate())
                    emit(
                        Agenda(
                            reminders = updatedLocalReminders,
                            tasks = updatedLocalTasks,
                            events = emptyList()
                        )
                    )
                }.onFailure {
                    // TODO: Internet error
                    println("")
                }
            }
        }
    }

    private suspend fun getAgendaRemotely(date: LocalDateTime) = resultOf {
        api.getAgenda(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }

    override suspend fun insertTask(task: Task, isEdit: Boolean) {
        dao.insertTask(task.toEntity())
        saveTaskRemotely(task = task, isEdit = isEdit).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    override suspend fun changeStatusTask(id: String, isDone: Boolean) {
        val task = getTaskById(id).copy(
            isDone = isDone
        )
        insertTask(task = task, isEdit = true)
    }

    private suspend fun saveTaskRemotely(task: Task, isEdit: Boolean) = resultOf {
        task.toDto().let {
            if (isEdit) {
                api.updateTask(it)
            } else {
                api.createTask(it)
            }
        }
    }

    override suspend fun getTaskById(id: String): Task {
        return dao.getTaskById(id).toDomain()
    }

    private suspend fun getTasksForDate(date: LocalDate): List<Task> {
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
