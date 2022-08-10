package com.mk.tasky.authentication.login.domain

import com.mk.tasky.authentication.login.domain.models.LoggedUser

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<LoggedUser>
}
