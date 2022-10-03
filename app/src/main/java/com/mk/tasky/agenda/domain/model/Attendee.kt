package com.mk.tasky.agenda.domain.model

import java.time.LocalDateTime

data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String,
    // TODO: Removed for testing locally. val eventId: String,
    val isGoing: Boolean,
    val remindAt: LocalDateTime
)
