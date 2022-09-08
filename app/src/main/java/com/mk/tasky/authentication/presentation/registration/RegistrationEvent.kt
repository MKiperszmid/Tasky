package com.mk.tasky.authentication.presentation.registration

sealed class RegistrationEvent {
    data class OnNameChange(val name: String) : RegistrationEvent()
    data class OnEmailChange(val email: String) : RegistrationEvent()
    data class OnPasswordChange(val password: String) : RegistrationEvent()
    object TogglePasswordVisibility : RegistrationEvent()
    object Submit : RegistrationEvent()
}
