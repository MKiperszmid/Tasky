package com.mk.tasky.authentication.presentation.login

data class LoginState(
    val email: String = "",
    val emailError: Boolean = false,
    val validEmail: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val isPasswordHidden: Boolean = true,
    val isLoggedIn: Boolean = false
)
