package com.mk.tasky.authentication.login.presentation

data class LoginState(
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val isPasswordHidden: Boolean = true
)
