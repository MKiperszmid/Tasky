package com.mk.tasky.authentication.domain.usecase

import com.mk.tasky.core.domain.usecase.ValidateEmailUseCase

data class FormValidatorUseCase(
    val validEmail: ValidateEmailUseCase,
    val validPassword: ValidatePasswordUseCase,
    val validFullname: ValidateFullNameUseCase
)
