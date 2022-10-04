package com.mk.tasky.agenda.data.remote.dto


import com.squareup.moshi.Json

data class AttendeeExistResponseDto(
    @field:Json(name = "attendee")
    val attendee: AttendeeDto,
    @field:Json(name = "doesUserExist")
    val doesUserExist: Boolean
)