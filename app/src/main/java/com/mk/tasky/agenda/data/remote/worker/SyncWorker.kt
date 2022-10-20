package com.mk.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.SyncItem
import com.mk.tasky.agenda.domain.model.SyncType
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.uploader.EventUploader
import com.mk.tasky.core.data.util.resultOf
import com.mk.tasky.core.util.AgendaItemType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception


@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val repository: AgendaRepository,
    private val eventUploader: EventUploader
) : CoroutineWorker(context, workerParameters) {
    companion object {
        const val WORKER_ID = "sync_worker"
    }

    override suspend fun doWork(): Result {
        val items = repository.getAllSyncableItems()
        return try {
            supervisorScope {
                val jobs = items.map { launch { completeAction(it) } }
                jobs.forEach { it.join() }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun completeAction(syncItem: SyncItem) {
        if (syncItem.type == SyncType.DELETE) {
            deleteItem(syncItem).onSuccess {
                repository.deleteSyncableItem(syncItem.id)
            }
            return
        }

        val item = when (syncItem.agendaItemType) {
            AgendaItemType.EVENT -> repository.getEventById(syncItem.id)
            AgendaItemType.REMINDER -> repository.getReminderById(syncItem.id)
            AgendaItemType.TASK -> repository.getTaskById(syncItem.id)
        }
        val runner = when (syncItem.type) {
            SyncType.CREATE -> createItem(item)
            SyncType.UPDATE -> updateItem(item)
            else -> kotlin.Result.success(Unit) // Already covered, never going to go through this case
        }

        runner.onSuccess {
            repository.deleteSyncableItem(syncItem.id)
        }

    }

    private suspend fun deleteItem(syncItem: SyncItem) = resultOf {
        when (syncItem.agendaItemType) {
            AgendaItemType.EVENT -> repository.deleteEventById(syncItem.id)
            AgendaItemType.REMINDER -> repository.deleteReminderById(syncItem.id)
            AgendaItemType.TASK -> repository.deleteTaskById(syncItem.id)
        }
    }

    private suspend fun updateItem(agendaItem: AgendaItem) = resultOf {
        when (agendaItem) {
            is AgendaItem.Event -> eventUploader.uploadEvent(agendaItem, true)
            is AgendaItem.Reminder -> repository.insertReminder(agendaItem, true)
            is AgendaItem.Task -> repository.insertTask(agendaItem, true)
        }
    }

    private suspend fun createItem(agendaItem: AgendaItem) = resultOf {
        when (agendaItem) {
            is AgendaItem.Event -> eventUploader.uploadEvent(agendaItem, false)
            is AgendaItem.Reminder -> repository.insertReminder(agendaItem, false)
            is AgendaItem.Task -> repository.insertTask(agendaItem, false)
        }
    }
}