package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class TaskDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "time")
    val time: Long,
    @field:Json(name = "remindAt")
    val remindAt: Long,
    @field:Json(name = "isDone")
    val isDone: Boolean
)
