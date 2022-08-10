package com.mk.tasky.authentication.login.domain.models

data class LoggedUser(
    val token: String,
    val userId: String,
    val fullName: String
)
