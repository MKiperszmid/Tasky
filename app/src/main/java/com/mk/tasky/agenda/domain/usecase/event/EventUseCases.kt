package com.mk.tasky.agenda.domain.usecase.event

data class EventUseCases(
    val saveEvent: SaveEvent,
    val getAttendee: GetAttendee
)
