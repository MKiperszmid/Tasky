package com.mk.tasky.agenda.data.uri

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap
import com.mk.tasky.agenda.domain.uri.PhotoExtensionParser
import java.io.File
import javax.inject.Inject

class PhotoExtensionParserImpl @Inject constructor(
    private val application: Application
) : PhotoExtensionParser {
    override suspend fun extensionFromUri(uri: Uri): String? {
        return if (uri.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            // If scheme is a content
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(application.contentResolver.getType(uri))
        } else {
            // If scheme is a File
            uri.path?.let {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString())
            }
        }
    }
}
