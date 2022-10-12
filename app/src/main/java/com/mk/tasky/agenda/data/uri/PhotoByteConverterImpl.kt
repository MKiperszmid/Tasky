package com.mk.tasky.agenda.data.uri

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mk.tasky.agenda.domain.uri.PhotoByteConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PhotoByteConverterImpl @Inject constructor(
    private val application: Application,
    private val dispatcher: CoroutineDispatcher
) : PhotoByteConverter {
    private suspend fun bitmapFromUri(uri: Uri): Bitmap {
        return withContext(dispatcher) {
            val bytes = application.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: byteArrayOf()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }

    override suspend fun uriToBytes(uri: Uri): ByteArray {
        return withContext(dispatcher) {
            val bitmap = bitmapFromUri(uri)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // TODO: Test different compression settings
            outputStream.toByteArray()
        }
    }
}
