package com.mk.tasky.authentication.domain.usecase

data class FormValidatorUseCase(
    val validEmail: ValidateEmailUseCase,
    val validPassword: ValidatePasswordUseCase,
    val validFullname: ValidateFullNameUseCase
)
