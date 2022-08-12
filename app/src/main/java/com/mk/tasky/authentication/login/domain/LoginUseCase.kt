package com.mk.tasky.authentication.login.domain

import com.mk.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase

data class LoginUseCase(
    val validEmail: ValidateEmailUseCase,
    val validPassword: ValidatePasswordUseCase
)