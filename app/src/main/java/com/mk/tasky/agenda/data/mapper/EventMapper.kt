package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.EventEntity
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.toCurrentTime
import com.mk.tasky.agenda.util.toLong

fun AgendaItem.Event.toEntity(): EventEntity {
    return EventEntity(
        eventId = this.eventId,
        title = this.eventTitle,
        description = this.eventDescription,
        remindAt = this.eventRemindAt.toLong(),
        fromDateTime = this.eventFromDateTime.toLong(),
        toDateTime = this.eventToDateTime.toLong(),
        // attendees = this.attendees.map { it.toEntity() },
        // photos = this.photos.map { it.toEntity() },
        hostId = this.hostId,
        isHost = this.isHost
    )
}

fun EventEntity.toDomain(): AgendaItem.Event {
    return AgendaItem.Event(
        eventId = this.eventId,
        eventDescription = this.description,
        eventTitle = this.title,
        eventFromDateTime = this.fromDateTime.toCurrentTime(),
        eventToDateTime = this.toDateTime.toCurrentTime(),
        eventRemindAt = this.remindAt.toCurrentTime(), // TODO: Receive list of attendees and photos to later add on params
        attendees = emptyList(), // this.attendees.map { it.toDomain() },
        photos = emptyList(), // this.photos.map { it.toDomain() },
        hostId = this.hostId,
        isHost = this.isHost
    )
}
