package com.mk.tasky.authentication.domain.models

data class LoggedUser(
    val token: String,
    val userId: String,
    val fullName: String
)
