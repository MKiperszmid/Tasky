package com.mk.tasky.authentication.login.data

import com.mk.tasky.authentication.login.data.mapper.toDomain
import com.mk.tasky.authentication.login.data.remote.LoginApi
import com.mk.tasky.authentication.login.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.login.domain.LoginRepository
import com.mk.tasky.authentication.login.domain.models.LoggedUser

class LoginRepositoryImpl(
    private val api: LoginApi
) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<LoggedUser> {
        val body = LoginBodyDto(email = email, password = password)
        return try {
            val response = api.login(body)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
