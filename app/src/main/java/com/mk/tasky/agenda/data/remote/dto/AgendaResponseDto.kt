package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class AgendaResponseDto(
    // TODO: Fix with events and tasks
    @field:Json(name = "events")
    val events: List<Any>,
    @field:Json(name = "reminders")
    val reminders: List<ReminderDto>,
    @field:Json(name = "tasks")
    val tasks: List<Any>
)
