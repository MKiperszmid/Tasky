package com.mk.tasky.agenda.data.remote.sync

import androidx.work.*
import com.mk.tasky.agenda.data.remote.worker.SyncWorker
import com.mk.tasky.agenda.data.remote.worker.SyncWorker.Companion.WORKER_ID
import com.mk.tasky.agenda.domain.sync.AgendaSync
import java.time.Duration

class AgendaSyncImpl(
    private val workManager: WorkManager
) : AgendaSync {
    override suspend fun syncAgenda() {
        val uploaderWorker = OneTimeWorkRequestBuilder<SyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(1)).build()

        workManager.beginUniqueWork(
            WORKER_ID,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            uploaderWorker
        ).enqueue()
    }
}