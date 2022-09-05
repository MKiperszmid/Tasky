package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.mk.tasky.agenda.domain.model.Agenda
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.util.ErrorParser
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.CancellationException

class AgendaRepositoryImpl(
    private val dao: AgendaDao,
    private val api: AgendaApi
) : AgendaRepository {
    override suspend fun insertReminder(reminder: Reminder, isEdit: Boolean): Result<Unit> {
        dao.insertReminder(reminder.toEntity())
        return saveReminderRemotely(reminder, isEdit)
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

    override suspend fun deleteReminderById(id: String): Result<Unit> {
        dao.deleteReminderById(id)
        return deleteReminderByIdRemotely(id)
    }

    override suspend fun getAgenda(date: LocalDateTime, forceRemote: Boolean): Agenda {
        // TODO: Update with Tasks and Events
        val reminders = getRemindersForDate(date.toLocalDate()).toMutableList()
        if (forceRemote) {
            getAgendaRemotely(date).onSuccess { response ->
                reminders.addAll(response.reminders.map { it.toDomain() })
            }.onFailure {
                // TODO: Internet error
                println("")
            }
        }

        return Agenda(reminders = reminders, tasks = emptyList(), events = emptyList())
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
}
