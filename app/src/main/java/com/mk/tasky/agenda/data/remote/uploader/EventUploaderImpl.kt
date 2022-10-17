package com.mk.tasky.agenda.data.remote.uploader

import androidx.work.*
import com.mk.tasky.agenda.data.mapper.toDto
import com.mk.tasky.agenda.data.remote.dto.EventDto
import com.mk.tasky.agenda.data.remote.worker.EventUploaderWorker
import com.mk.tasky.agenda.data.remote.worker.EventUploaderWorkerParameters
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.uploader.EventUploder
import com.squareup.moshi.Moshi
import java.time.Duration

class EventUploaderImpl(
    private val workManager: WorkManager
) : EventUploder {
    override suspend fun uploadEvent(event: AgendaItem.Event, isEdit: Boolean) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(EventDto::class.java)
        val json: String = jsonAdapter.toJson(event.toDto())

        val photoLocations = event.photos.map { it.location }
        val uploaderWorker = OneTimeWorkRequestBuilder<EventUploaderWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setInputData(
            Data.Builder()
                .putString(EventUploaderWorkerParameters.EVENT_JSON, json)
                .putBoolean(EventUploaderWorkerParameters.IS_EDIT, isEdit)
                .putStringArray(
                    EventUploaderWorkerParameters.EVENT_PHOTO_ARRAY,
                    photoLocations.toTypedArray()
                ).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(1)).build()

        workManager.beginUniqueWork(
            "event_uploader",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            uploaderWorker
        ).enqueue()
    }
}
