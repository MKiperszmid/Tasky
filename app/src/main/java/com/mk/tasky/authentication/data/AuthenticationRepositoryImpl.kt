package com.mk.tasky.authentication.data

import com.mk.tasky.authentication.data.mapper.toDomain
import com.mk.tasky.authentication.data.remote.AuthenticationApi
import com.mk.tasky.authentication.data.remote.dto.ErrorResponseDto
import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.data.remote.exceptions.LoginException
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.domain.models.LoggedUser
import com.squareup.moshi.Moshi
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

    private fun parseError(e: HttpException): Result<LoggedUser> {
        e.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponseDto::class.java)
            try {
                val body = moshiAdapter.fromJson(it)
                body?.message?.let { errorMessage ->
                    return Result.failure(LoginException(errorMessage))
                }
            } catch (e: Exception) {
            }
        }
        return Result.failure(e)
    }
}
