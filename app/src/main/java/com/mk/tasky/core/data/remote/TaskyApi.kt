package com.mk.tasky.core.data.remote

import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.data.remote.dto.LoginResponseDto
import com.mk.tasky.authentication.data.remote.dto.RegistrationBodyDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyApi {
    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }

    @POST("/login")
    suspend fun login(
        @Body body: LoginBodyDto
    ): LoginResponseDto

    @POST("/register")
    suspend fun register(
        @Body body: RegistrationBodyDto
    )

    @GET("/authenticate")
    suspend fun authenticate()
}
