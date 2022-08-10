package com.mk.tasky.authentication.login.data.mapper

import com.mk.tasky.authentication.login.data.remote.dto.LoginResponseDto
import com.mk.tasky.authentication.login.domain.models.LoggedUser

fun LoginResponseDto.toDomain(): LoggedUser {
    return LoggedUser(
        token = this.token,
        userId = this.userId,
        fullName = this.fullName
    )
}
