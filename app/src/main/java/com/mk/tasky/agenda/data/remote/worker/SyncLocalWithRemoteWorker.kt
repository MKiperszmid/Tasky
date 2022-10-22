package com.mk.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mk.tasky.agenda.data.local.AgendaDao
import com.mk.tasky.agenda.data.local.entity.relations.EventAttendeesCrossRef
import com.mk.tasky.agenda.data.mapper.toDomain
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.mapper.toEntity
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.SyncItem
import com.mk.tasky.agenda.domain.model.SyncType
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.uploader.EventUploader
import com.mk.tasky.agenda.util.toLong
import com.mk.tasky.core.data.util.resultOf
import com.mk.tasky.core.util.AgendaItemType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


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
        var localItem: AgendaItem? = null
        when(item) {
            is AgendaItem.Reminder -> {
                localItem = try {
                    dao.getReminderById(item.id).toDomain()
                } catch (e: Exception) {
                    null
                }
                dao.insertReminder(item.toEntity())
            }
            is AgendaItem.Task -> {
                localItem = try {
                    dao.getTaskById(item.id).toDomain()
                } catch (e: Exception) {
                    null
                }
                dao.insertTask(item.toEntity())
            }
            is AgendaItem.Event -> {
                localItem = try {
                    dao.getEventById(item.id).toDomain()
                } catch (e: Exception) {
                    null
                }
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

        if (localItem == null) {
            alarmRegister.setAlarm(item)
        } else {
            alarmRegister.updateAlarm(newItem = item, previousItem = localItem)
        }
    }

    private suspend fun getAgenda() = resultOf {
        val today = LocalDateTime.now().toLong()
        api.getAgenda(today)
    }
}