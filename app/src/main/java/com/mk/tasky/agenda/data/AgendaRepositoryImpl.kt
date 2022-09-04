package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.core.util.ErrorParser
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.time.LocalDate
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

    override suspend fun getRemindersForDate(date: LocalDate): List<Reminder> {
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
    }
}
