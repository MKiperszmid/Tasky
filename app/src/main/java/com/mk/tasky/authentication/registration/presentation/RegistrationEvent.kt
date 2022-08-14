package com.mk.tasky.authentication.registration.presentation

sealed class RegistrationEvent {
    data class OnNameChange(val name: String) : RegistrationEvent()
    data class OnEmailChange(val email: String) : RegistrationEvent()
    data class OnPasswordChange(val password: String) : RegistrationEvent()
    object ChangePasswordVisibility : RegistrationEvent()
    object Submit : RegistrationEvent()
}
