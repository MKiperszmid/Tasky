package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

sealed class EventDto(
    @field:Json(name = "id")
    open val id: String,
    @field:Json(name = "title")
    open val title: String,
    @field:Json(name = "description")
    open val description: String,
    @field:Json(name = "from")
    open val from: Long,
    @field:Json(name = "to")
    open val to: Long,
    @field:Json(name = "remindAt")
    open val remindAt: Long,
    @field:Json(name = "attendeeIds")
    open val attendeeIds: List<String>
) {
    data class CreateEventDto(
        override val id: String,
        override val title: String,
        override val description: String,
        override val from: Long,
        override val to: Long,
        override val remindAt: Long,
        override val attendeeIds: List<String>
    ) : EventDto(id, title, description, from, to, remindAt, attendeeIds)

    data class UpdateEventDto(
        override val id: String,
        override val title: String,
        override val description: String,
        override val from: Long,
        override val to: Long,
        override val remindAt: Long,
        override val attendeeIds: List<String>,
        @field:Json(name = "deletedPhotoKeys")
        val deletedPhotoKeys: List<String>,
        @field:Json(name = "isGoing")
        val isGoing: Boolean
    ) : EventDto(id, title, description, from, to, remindAt, attendeeIds)

}