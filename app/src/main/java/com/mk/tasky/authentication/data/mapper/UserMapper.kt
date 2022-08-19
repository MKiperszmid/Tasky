package com.mk.tasky.authentication.data.mapper

import com.mk.tasky.authentication.data.remote.dto.LoginResponseDto
import com.mk.tasky.core.domain.model.LoggedUser

fun LoginResponseDto.toDomain(): LoggedUser {
    return LoggedUser(
        token = this.token,
        userId = this.userId,
        fullName = this.fullName
    )
}
