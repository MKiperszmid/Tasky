package com.mk.tasky.agenda.data.remote.sync

import androidx.work.*
import com.mk.tasky.agenda.data.remote.worker.SyncWorker
import com.mk.tasky.agenda.domain.sync.AgendaSync
import java.time.Duration
import java.util.*

class AgendaSyncImpl(
    private val workManager: WorkManager
) : AgendaSync {
    override suspend fun syncAgenda(): UUID {
        val syncWorker = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()
        workManager.beginUniqueWork(
            "sync_worker",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            syncWorker
        ).enqueue()
        return syncWorker.id
    }
}