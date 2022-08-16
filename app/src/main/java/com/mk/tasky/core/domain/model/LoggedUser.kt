package com.mk.tasky.core.domain.model

data class LoggedUser(
    val token: String?,
    val userId: String?,
    val fullName: String?
)
