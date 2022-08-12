package com.mk.tasky.authentication.data.remote.dto

import com.squareup.moshi.Json

data class ErrorResponseDto(
    @Json(name = "message")
    val message: String
)
