package com.mk.tasky.authentication.data.remote.dto


import com.squareup.moshi.Json

data class LoginResponseDto(
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "token")
    val token: String,
    @field:Json(name = "userId")
    val userId: String
)