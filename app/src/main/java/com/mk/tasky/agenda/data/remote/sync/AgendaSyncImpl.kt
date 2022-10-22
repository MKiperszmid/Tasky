package com.mk.tasky.agenda.data.remote.sync

import androidx.work.*
import com.mk.tasky.agenda.data.remote.worker.SyncLocalWithRemoteWorker
import com.mk.tasky.agenda.data.remote.worker.SyncRemoteWithLocalWorker
import com.mk.tasky.agenda.domain.sync.AgendaSync
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

class AgendaSyncImpl(
    private val workManager: WorkManager
) : AgendaSync {
    override fun syncAgendaRemoteWithLocal(): UUID {
        val syncWorker = OneTimeWorkRequestBuilder<SyncRemoteWithLocalWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()
        workManager.beginUniqueWork(
            "sync_worker_local_to_remote",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            syncWorker
        ).enqueue()
        return syncWorker.id
    }

    override fun syncAgendaLocalWithRemote(): UUID {
        val syncWorker = PeriodicWorkRequestBuilder<SyncLocalWithRemoteWorker>(
            15, TimeUnit.MINUTES,
            5, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5)).build()

        workManager.enqueueUniquePeriodicWork(
            "sync_worker_remote_to_local",
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorker
        )
        return syncWorker.id
    }
}