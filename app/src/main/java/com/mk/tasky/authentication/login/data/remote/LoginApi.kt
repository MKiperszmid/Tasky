package com.mk.tasky.authentication.login.data.remote

import com.mk.tasky.authentication.login.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.login.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }

    @POST("/login")
    suspend fun login(
        @Body body: LoginBodyDto
    ): LoginResponseDto
}
