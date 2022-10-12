package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.EventEntity
import com.mk.tasky.agenda.data.local.entity.relations.EventWithAttendees
import com.mk.tasky.agenda.data.remote.dto.EventDto
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.Attendee
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
        // photos = this.photos.map { it.toEntity() }, // TODO: Add support to Photos in the future
        hostId = this.hostId,
        isHost = this.isHost
    )
}

fun EventEntity.toDomain(attendees: List<Attendee>): AgendaItem.Event {
    return AgendaItem.Event(
        eventId = this.eventId,
        eventDescription = this.description,
        eventTitle = this.title,
        eventFromDateTime = this.fromDateTime.toCurrentTime(),
        eventToDateTime = this.toDateTime.toCurrentTime(),
        eventRemindAt = this.remindAt.toCurrentTime(), // TODO: Receive list of attendees and photos to later add on params
        attendees = attendees,
        photos = emptyList(), // this.photos.map { it.toDomain() },
        hostId = this.hostId,
        isHost = this.isHost
    )
}

fun EventWithAttendees.toDomain(): AgendaItem.Event {
    val attendees = this.attendees.map { it.toDomain() }
    return event.toDomain(attendees)
}

fun AgendaItem.Event.toDto(): EventDto {
    return EventDto(
        id = this.eventId,
        title = this.eventTitle,
        description = this.eventDescription,
        remindAt = this.eventRemindAt.toLong(),
        from = this.eventFromDateTime.toLong(),
        to = this.eventToDateTime.toLong(),
        attendeeIds = this.attendees.map { it.userId }
    )
}
