package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

sealed class EventDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "from")
    val from: Long,
    @field:Json(name = "to")
    val to: Long,
    @field:Json(name = "remindAt")
    val remindAt: Long,
    @field:Json(name = "attendeeIds")
    val attendeeIds: List<String>
) {
    data class CreateEventDto(
        val eventId: String,
        val eventTitle: String,
        val eventDescription: String,
        val eventFrom: Long,
        val eventTo: Long,
        val eventRemindAt: Long,
        val eventAttendeeIds: List<String>
    ) : EventDto(eventId, eventTitle, eventDescription, eventFrom, eventTo, eventRemindAt, eventAttendeeIds)
    data class UpdateEventDto(
        val eventId: String,
        val eventTitle: String,
        val eventDescription: String,
        val eventFrom: Long,
        val eventTo: Long,
        val eventRemindAt: Long,
        val eventAttendeeIds: List<String>,
        @field:Json(name = "deletedPhotoKeys")
        val deletedPhotoKeys: List<String>,
        @field:Json(name = "isGoing")
        val isGoing: Boolean
    ) : EventDto(eventId, eventTitle, eventDescription, eventFrom, eventTo, eventRemindAt, eventAttendeeIds)

}