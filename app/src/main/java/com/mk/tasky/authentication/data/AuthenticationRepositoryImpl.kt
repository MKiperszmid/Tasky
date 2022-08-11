package com.mk.tasky.authentication.data

import com.mk.tasky.authentication.data.mapper.toDomain
import com.mk.tasky.authentication.data.remote.AuthenticationApi
import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.domain.models.LoggedUser
import java.util.concurrent.CancellationException

class AuthenticationRepositoryImpl(
    private val api: AuthenticationApi
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Result<LoggedUser> {
        val body = LoginBodyDto(email = email, password = password)
        return try {
            val response = api.login(body)
            Result.success(response.toDomain())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
