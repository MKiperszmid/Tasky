package com.mk.tasky.core.data

import com.mk.tasky.authentication.data.mapper.toDomain
import com.mk.tasky.authentication.data.remote.dto.LoginBodyDto
import com.mk.tasky.authentication.data.remote.dto.RegistrationBodyDto
import com.mk.tasky.core.data.remote.AuthenticationApi
import com.mk.tasky.core.domain.repository.AuthenticationRepository
import com.mk.tasky.core.util.resultOf

class AuthenticationRepositoryImpl(
    private val api: AuthenticationApi
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String) = resultOf {
        val body = LoginBodyDto(email = email, password = password)
        api.login(body).toDomain()
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ) = resultOf {
        val body = RegistrationBodyDto(fullName = name, email = email, password = password)
        api.register(body)
    }

    override suspend fun authenticate() = resultOf {
        api.authenticate()
    }
}
