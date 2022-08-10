package com.mk.tasky.authentication.data.remote.dto


import com.squareup.moshi.Json

data class LoginBodyDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "password")
    val password: String
)