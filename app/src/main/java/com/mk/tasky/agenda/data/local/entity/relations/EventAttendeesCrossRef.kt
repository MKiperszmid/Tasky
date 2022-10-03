package com.mk.tasky.agenda.data.local.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["attendeeId", "eventId"])
data class EventAttendeesCrossRef(
    val eventId: String,
    val attendeeId: String
)
