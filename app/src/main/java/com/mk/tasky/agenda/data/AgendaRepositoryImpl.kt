package com.mk.tasky.agenda.data

import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.domain.model.Reminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class AgendaRepositoryImpl(
    private val dao: AgendaDao
) : AgendaRepository {
    override suspend fun insertReminder(reminder: Reminder) {
        dao.insertReminder(reminder.toEntity())
    }

    override fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>> {
        return dao.getRemindersForDate(
            day = date.dayOfMonth,
            month = date.monthValue,
            year = date.year
        ).map { reminders -> reminders.map { it.toDomain() } }
    }
}
