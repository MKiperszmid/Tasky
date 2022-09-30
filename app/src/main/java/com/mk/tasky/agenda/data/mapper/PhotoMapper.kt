package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.PhotoEntity
import com.mk.tasky.agenda.domain.model.AgendaPhoto

fun AgendaPhoto.toEntity(eventId: String): PhotoEntity {
    val key = if (this is AgendaPhoto.Local) null else (this as AgendaPhoto.Remote).key
    return PhotoEntity(
        location = this.location,
        key = key,
        eventId = eventId
    )
}

fun PhotoEntity.toDomain(): AgendaPhoto {
    return if (key == null) {
        AgendaPhoto.Local(this.location)
    } else {
        AgendaPhoto.Remote(remoteUrl = this.location, key = this.key)
    }
}
