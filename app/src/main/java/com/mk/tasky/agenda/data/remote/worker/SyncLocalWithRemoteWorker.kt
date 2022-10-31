package com.mk.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.local.entity.relations.EventAttendeesCrossRef
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.toLong
import com.mk.tasky.core.data.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDateTime

@HiltWorker
class SyncLocalWithRemoteWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: AgendaApi,
    private val dao: AgendaDao,
    private val alarmRegister: AlarmRegister
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val agenda = getAgenda()
        val agendaDto = agenda.getOrNull()
        if (agenda.isFailure || agendaDto == null) return Result.retry()

        val events = agendaDto.events.map { it.toDomain() }
        val reminders = agendaDto.reminders.map { it.toDomain() }
        val tasks = agendaDto.tasks.map { it.toDomain() }
        val items = events + reminders + tasks
        supervisorScope {
            val jobs = items.map { launch { saveItem(it) } }
            jobs.forEach { it.join() }
        }
        return Result.success()
    }

    private suspend fun saveItem(item: AgendaItem) {
        when (item) {
            is AgendaItem.Reminder -> {
                dao.insertReminder(item.toEntity())
            }
            is AgendaItem.Task -> {
                dao.insertTask(item.toEntity())
            }
            is AgendaItem.Event -> {
                supervisorScope {
                    val attendees = item.attendees.map {
                        launch { dao.insertAttendee(it.toEntity()) }
                        launch {
                            dao.insertEventAttendeeCrossRef(
                                EventAttendeesCrossRef(
                                    item.eventId,
                                    it.userId
                                )
                            )
                        }
                    }
                    attendees.forEach { it.join() }
                    dao.insertEvent(item.toEntity())
                }
            }
        }
        alarmRegister.setAlarm(item)
    }

    private suspend fun getAgenda() = resultOf {
        val today = LocalDateTime.now().toLong()
        api.getAgenda(today)
    }
}
