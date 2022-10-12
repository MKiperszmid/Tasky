package com.mk.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class PhotoDto(
    @field:Json(name = "key")
    val key: String,
    @field:Json(name = "url")
    val url: String
)
