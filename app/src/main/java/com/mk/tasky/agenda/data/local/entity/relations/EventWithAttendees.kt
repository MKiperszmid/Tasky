package com.mk.tasky.agenda.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mk.tasky.agenda.data.local.entity.AttendeeEntity
import com.mk.tasky.agenda.data.local.entity.EventEntity

data class EventWithAttendees(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "attendeeId",
        associateBy = Junction(EventAttendeesCrossRef::class)
    )
    val attendees: List<AttendeeEntity>
)
