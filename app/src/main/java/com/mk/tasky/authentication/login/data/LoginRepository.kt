package com.mk.tasky.authentication.login.data

import com.mk.tasky.authentication.login.data.remote.LoginApi
import com.mk.tasky.authentication.login.data.remote.dto.LoginBodyDto

class LoginRepository(
    private val api: LoginApi
) {
    suspend fun login(email: String, password: String): Result<Boolean> {
        val body = LoginBodyDto(email = email, password = password)
        return try {
            val response = api.login(body)
            println(response)
            Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
