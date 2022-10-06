package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.AttendeeEntity
import com.mk.tasky.agenda.data.remote.dto.AttendeeDto
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.util.toCurrentTime
import com.mk.tasky.agenda.util.toLong
import java.time.LocalDateTime

// TODO: Event ID should be removed from here and be added to the attendee as soon as they get added on the event
fun Attendee.toEntity(): AttendeeEntity {
    return AttendeeEntity(
        attendeeId = this.userId,
        fullName = this.fullName,
        userId = this.userId,
        isGoing = this.isGoing,
        email = this.email,
        remindAt = this.remindAt.toLong()
    )
}

fun AttendeeEntity.toDomain(): Attendee {
    return Attendee(
        email = this.email,
        fullName = this.fullName,
        userId = this.attendeeId,
        isGoing = this.isGoing,
        remindAt = this.remindAt.toCurrentTime()
    )
}

fun AttendeeDto.toDomain(): Attendee {
    return Attendee(
        email = this.email,
        fullName = this.fullName,
        userId = this.userId,
        isGoing = this.isGoing ?: true,
        remindAt = this.remindAt?.toCurrentTime() ?: LocalDateTime.now()
    )
}
