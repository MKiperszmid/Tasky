package com.mk.tasky.authentication.login.domain

import com.mk.tasky.authentication.domain.usecase.ValidEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidPasswordUseCase

data class LoginUseCase(
    val validEmail: ValidEmailUseCase,
    val validPassword: ValidPasswordUseCase
)