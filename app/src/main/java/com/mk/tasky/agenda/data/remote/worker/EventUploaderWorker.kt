package com.mk.tasky.agenda.data.remote.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.data.remote.worker.EventUploaderWorkerParameters.EVENT_JSON
import com.mk.tasky.agenda.data.remote.worker.EventUploaderWorkerParameters.EVENT_PHOTO_ARRAY
import com.mk.tasky.agenda.data.remote.worker.EventUploaderWorkerParameters.IS_EDIT
import com.mk.tasky.agenda.domain.uri.PhotoByteConverter
import com.mk.tasky.agenda.domain.uri.PhotoExtensionParser
import com.mk.tasky.core.data.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

@HiltWorker
class EventUploaderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val photoExtensionParser: PhotoExtensionParser,
    private val photoByteConverter: PhotoByteConverter,
    private val api: AgendaApi
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val isEdit = workerParameters.inputData.getBoolean(IS_EDIT, false)
        val eventJson = workerParameters.inputData.getString(EVENT_JSON) ?: return Result.failure()
        val photos = workerParameters.inputData.getStringArray(EVENT_PHOTO_ARRAY) ?: emptyArray()

        val photoFiles = locationToMultipart(photos.toList())

        val result = uploadEvent(eventJson, photoFiles, isEdit)
        return if (result.isSuccess) {
            Log.d("EVENT_UPLOADER_WORKER", "Event created successfully!")
            Result.success()
        } else {
            val exceptionMessage = result.exceptionOrNull()?.message
            Log.d("EVENT_UPLOADER_WORKER", "Event creation failed: $exceptionMessage")
            // TODO: Add a retry X times (3-5) in case of error 5XX
            // TODO: Save id on db to later sync with server
            Result.failure()
        }
    }

    private suspend fun locationToMultipart(photos: List<String>): List<MultipartBody.Part> {
        return photos.mapIndexed { index, photoLocation ->
            val uri = Uri.parse(photoLocation)
            val extension = photoExtensionParser.extensionFromUri(uri)
            val uriBytes = photoByteConverter.uriToBytes(uri)

            MultipartBody.Part.createFormData(
                "photo$index",
                filename = UUID.randomUUID().toString() + "." + extension,
                uriBytes.toRequestBody()
            )
        }
    }

    private suspend fun uploadEvent(
        json: String,
        photos: List<MultipartBody.Part>,
        isEdit: Boolean
    ) = resultOf {
        // TODO: Add isEdit to call create or update event.
        api.createEvent(
            body = MultipartBody.Part.createFormData("create_event_request", json),
            files = photos
        )
    }
}
