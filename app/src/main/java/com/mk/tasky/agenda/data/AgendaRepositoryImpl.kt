package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.model.Task
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.util.ErrorParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.CancellationException

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

    private suspend fun saveReminderRemotely(reminder: Reminder, isEdit: Boolean): Result<Unit> {
        val reminderDto = reminder.toDto()
        return try {
            if (isEdit) {
                api.updateReminder(reminderDto)
            } else {
                api.createReminder(reminderDto)
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return ErrorParser.parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
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

    private suspend fun deleteReminderByIdRemotely(id: String): Result<Unit> {
        return try {
            api.deleteReminder(id)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return ErrorParser.parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getAgenda(date: LocalDateTime, forceRemote: Boolean): Flow<Agenda> {
        // TODO: Update with Tasks and Events
        return flow {
            val localReminders = getRemindersForDate(date.toLocalDate()).toMutableList()
            emit(Agenda(reminders = localReminders, tasks = emptyList(), events = emptyList()))
            if (forceRemote) {
                getAgendaRemotely(date).onSuccess { response ->
                    supervisorScope {
                        response.reminders.map {
                            launch { dao.insertReminder(it.toDomain().toEntity()) }
                        }.forEach { it.join() }
                    }

                    val updatedLocalReminders =
                        getRemindersForDate(date.toLocalDate()).toMutableList()
                    emit(
                        Agenda(
                            reminders = updatedLocalReminders,
                            tasks = emptyList(),
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

    private suspend fun getAgendaRemotely(date: LocalDateTime): Result<AgendaResponseDto> {
        return try {
            val response =
                api.getAgenda(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            Result.success(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return ErrorParser.parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
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

    private suspend fun saveTaskRemotely(task: Task, isEdit: Boolean): Result<Unit> {
        val dto = task.toDto()
        return try {
            if (isEdit) {
                api.updateTask(dto)
            } else {
                api.createTask(dto)
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return ErrorParser.parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getTaskById(id: String): Task {
        return dao.getTaskById(id).toDomain()
    }

    override suspend fun deleteTaskById(id: String) {
        dao.deleteTaskById(id)
        deleteTaskByIdRemotely(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }

    private suspend fun deleteTaskByIdRemotely(id: String): Result<Unit> {
        return try {
            api.deleteTask(id)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return ErrorParser.parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
