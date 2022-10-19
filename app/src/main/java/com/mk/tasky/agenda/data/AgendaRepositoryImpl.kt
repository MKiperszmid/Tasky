package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.local.entity.relations.EventAttendeesCrossRef
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.data.util.resultOf
import kotlinx.coroutines.delay
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
    private val api: AgendaApi,
    private val alarmRegister: AlarmRegister
) : AgendaRepository {
    // TODO: Have the functions receive AgendaItem, and based on the item call the API function, as to avoid duplicated functions
    override suspend fun insertReminder(reminder: AgendaItem.Reminder, isEdit: Boolean) {
        if (isEdit) {
            val currentReminder = getReminderById(reminder.id)
            alarmRegister.updateAlarm(newItem = reminder, previousItem = currentReminder)
        } else {
            alarmRegister.setAlarm(reminder)
        }
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
        val reminder = dao.getReminderById(id).toDomain()
        alarmRegister.cancelAlarm(reminder)
        dao.deleteReminderById(id)
        deleteReminderByIdRemotely(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun deleteReminderByIdRemotely(id: String) = resultOf {
        api.deleteReminder(id)
    }

    override fun getAgenda(date: LocalDate, forceRemote: Boolean): Flow<Agenda> {
        return flow {
            val localReminders = getRemindersForDate(date)
            val localTasks = getTasksForDate(date)
            val localEvents = getEventsForDate(date)
            emit(Agenda(localReminders + localTasks + localEvents))
            if (forceRemote) {
                getAgendaRemotely(date).onSuccess { response ->
                    supervisorScope {
                        // TODO: If there's an update on the remote side, then the alarmRegister won't be called on any of those
                        // Add a way to check if the item from remote is new or edit, and set the corresponding alarm
                        val reminders = response.reminders.map {
                            launch { dao.insertReminder(it.toDomain().toEntity()) }
                        }
                        val tasks = response.tasks.map {
                            launch { dao.insertTask(it.toDomain().toEntity()) }
                        }
                        val events = response.events.map {
                            launch { insertEvent(it.toDomain(), false) }
                        }
                        (reminders + tasks + events).forEach { it.join() }
                    }

                    val updatedLocalReminders = getRemindersForDate(date)
                    val updatedLocalTasks = getTasksForDate(date)
                    val updatedLocalEvents = getEventsForDate(date)
                    emit(Agenda(updatedLocalReminders + updatedLocalTasks + updatedLocalEvents))
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
        if (isEdit) {
            val currentTask = getTaskById(task.id)
            alarmRegister.updateAlarm(newItem = task, previousItem = currentTask)
        } else {
            alarmRegister.setAlarm(task)
        }
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
        val currentTask = dao.getTaskById(id).toDomain()
        alarmRegister.cancelAlarm(currentTask)
        dao.deleteTaskById(id)
        deleteTaskByIdRemotely(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    override suspend fun getAttendee(email: String) = resultOf {
        api.getAttendee(email).let {
            if (!it.doesUserExist) {
                null
            } else {
                it.attendee.toDomain()
            }
        }
    }

    private suspend fun deleteTaskByIdRemotely(id: String) = resultOf {
        api.deleteTask(id)
    }

    private suspend fun getEventsForDate(date: LocalDate): List<AgendaItem.Event> {
        val dayOne = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val dayTwo = date.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()

        return dao.getEventsBetweenTimestamps(
            dayOne = dayOne,
            dayTwo = dayTwo
        ).map {
            it.toDomain()
        }
    }

    override suspend fun getEventById(id: String): AgendaItem.Event {
        // TODO: Get the event remotely so it gets the images
        return dao.getEventById(id).toDomain()
    }

    override suspend fun insertEvent(event: AgendaItem.Event, isEdit: Boolean) {
        // Edge Cases:
        // - If we get event remotely with different attendees, we should remove all from db and add the new ones, since we could have outdated users
        supervisorScope {
            val attendees = event.attendees.map {
                launch { dao.insertAttendee(it.toEntity()) }
                launch {
                    dao.insertEventAttendeeCrossRef(
                        EventAttendeesCrossRef(
                            event.eventId,
                            it.userId
                        )
                    )
                }
            }
            attendees.forEach { it.join() }

            if (isEdit) {
                val currentEvent = getEventById(event.id)
                alarmRegister.updateAlarm(newItem = event, previousItem = currentEvent)
            } else {
                alarmRegister.setAlarm(event)
            }

            dao.insertEvent(event.toEntity())
        }
    }

    private suspend fun deleteEventLocaly(id: String) {
        dao.deleteEventById(id)
        dao.deleteEventCrossRefById(id)
    }

    override suspend fun deleteEventById(id: String) {
        val event = dao.getEventById(id).toDomain()
        alarmRegister.cancelAlarm(event)

        supervisorScope {
            val localJob = launch { deleteEventLocaly(id) }

            val remoteJob = launch {
                deleteEventByIdRemotely(id).onFailure {
                    println(it.message)
                // TODO: Save id on db to later sync with server
            } }
            remoteJob.join()
            localJob.join()
        }
    }

    private suspend fun deleteEventByIdRemotely(id: String) = resultOf {
        api.deleteEvent(id)
    }

    override suspend fun getAllUpcomingItems(): Agenda {
        val date =
            LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val reminders = dao.getAllFutureReminders(date).map { it.toDomain() }
        val tasks = dao.getAllFutureTasks(date).map { it.toDomain() }
        val events = dao.getAllFutureEvents(date).map { it.toDomain() }
        return Agenda(reminders + tasks + events)
    }

    // TODO: Delete and update event + alarmRegister on delete
}
