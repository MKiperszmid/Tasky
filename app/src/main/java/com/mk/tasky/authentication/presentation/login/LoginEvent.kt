package com.mk.tasky.authentication.presentation.login

sealed class LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object Submit : LoginEvent()
}
