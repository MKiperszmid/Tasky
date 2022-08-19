package com.mk.tasky.core.domain.repository

import com.mk.tasky.core.domain.model.LoggedUser

interface TaskyRepository {
    suspend fun login(email: String, password: String): Result<LoggedUser>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun authenticate(): Result<Unit>
}
