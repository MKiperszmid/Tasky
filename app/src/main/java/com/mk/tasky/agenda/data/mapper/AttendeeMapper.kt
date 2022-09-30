package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.AttendeeEntity
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.util.TimeUtil

// TODO: Event ID should be removed from here and be added to the attendee as soon as they get added on the event
fun Attendee.toEntity(eventId: String): AttendeeEntity {
    return AttendeeEntity(
        id = this.userId,
        fullName = this.fullName,
        userId = this.userId,
        isGoing = this.isGoing,
        eventId = eventId, // this.eventId,
        email = this.email,
        remindAt = TimeUtil.timeToLong(this.remindAt)
    )
}

fun AttendeeEntity.toDomain(): Attendee {
    return Attendee(
        email = this.email,
        fullName = this.fullName,
        userId = this.id,
        eventId = this.eventId,
        isGoing = this.isGoing,
        remindAt = TimeUtil.longToTime(this.remindAt)
    )
}
