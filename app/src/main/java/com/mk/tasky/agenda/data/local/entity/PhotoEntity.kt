package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    @PrimaryKey
    val location: String,
    val key: String?, // We could use a RemotePhotoEntity and LocalPhotoEntity but seems kinda overkill in this case. I can just check if key is null, then it's local, else it's remote.
    val eventId: String
)
