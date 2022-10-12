package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class EventResponseDto(
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
    @field:Json(name = "host")
    val host: String,
    @field:Json(name = "isUserEventCreator")
    val isUserEventCreator: Boolean,
    @field:Json(name = "attendees")
    val attendees: List<AttendeeDto>,
    @field:Json(name = "photos")
    val photos: List<PhotoDto>
)
