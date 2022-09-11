package com.mk.tasky.authentication.presentation.registration

data class RegistrationState(
    val name: String = "",
    val nameError: Boolean = false,
    val validName: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val validEmail: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val isPasswordHidden: Boolean = true
)
