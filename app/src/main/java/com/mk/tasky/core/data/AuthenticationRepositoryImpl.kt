package com.mk.tasky.core.data

import com.mk.tasky.authentication.data.mapper.toDomain
import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.data.remote.dto.RegistrationBodyDto
import com.mk.tasky.core.data.remote.AuthenticationApi
import com.mk.tasky.core.domain.model.LoggedUser
import com.mk.tasky.core.domain.repository.AuthenticationRepository
import com.mk.tasky.core.util.ErrorParser.parseError
import retrofit2.HttpException
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
        } catch (e: HttpException) {
            return parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        val body = RegistrationBodyDto(fullName = name, email = email, password = password)
        return try {
            api.register(body)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun authenticate(): Result<Unit> {
        return try {
            api.authenticate()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            return parseError(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
