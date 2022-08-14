package com.mk.tasky.authentication.registration.domain

import com.mk.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidateFullNameUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase

data class RegistrationUseCase(
    val validEmail: ValidateEmailUseCase,
    val validPassword: ValidatePasswordUseCase,
    val validFullname: ValidateFullNameUseCase
)
