package com.mk.tasky.authentication.data.remote

import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }

    @POST("/login")
    suspend fun login(
        @Body body: LoginBodyDto
    ): LoginResponseDto
}
