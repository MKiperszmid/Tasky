package com.mk.tasky.authentication.domain

import com.mk.tasky.authentication.domain.models.LoggedUser

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Result<LoggedUser>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
}
