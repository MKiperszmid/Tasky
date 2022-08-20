package com.mk.tasky.core.domain.repository

import com.mk.tasky.core.domain.model.LoggedUser

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Result<LoggedUser>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun authenticate(): Result<Unit>
}
