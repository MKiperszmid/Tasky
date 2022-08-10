package com.mk.tasky.authentication.domain

import com.mk.tasky.authentication.domain.models.LoggedUser

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Result<LoggedUser>
}
