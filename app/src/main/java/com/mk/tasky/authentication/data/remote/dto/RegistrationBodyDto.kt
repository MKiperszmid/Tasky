package com.mk.tasky.authentication.data.remote.dto


import com.squareup.moshi.Json

data class RegistrationBodyDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "password")
    val password: String
)