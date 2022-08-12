package com.mk.tasky.authentication.login.presentation

sealed class LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    object ChangePasswordVisibility : LoginEvent()
    object Submit : LoginEvent()
}
