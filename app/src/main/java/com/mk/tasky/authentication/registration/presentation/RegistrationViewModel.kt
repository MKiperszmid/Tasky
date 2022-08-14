package com.mk.tasky.authentication.registration.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.registration.domain.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {
    var state by mutableStateOf(RegistrationState())
        private set

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnNameChange -> {
                state = state.copy(
                    name = event.name,
                    nameError = false,
                    validName = registrationUseCase.validFullname(event.name)
                )
            }

            is RegistrationEvent.OnEmailChange -> {
                state = state.copy(
                    email = event.email,
                    emailError = false,
                    validEmail = registrationUseCase.validEmail(event.email)
                )
            }
            is RegistrationEvent.OnPasswordChange -> {
                state = state.copy(
                    password = event.password,
                    passwordError = false
                )
            }
            is RegistrationEvent.ChangePasswordVisibility -> {
                state = state.copy(
                    isPasswordHidden = !state.isPasswordHidden
                )
            }
            is RegistrationEvent.Submit -> {
                if (!registrationUseCase.validFullname(state.name)) {
                    state = state.copy(
                        nameError = true
                    )
                }
                if (!registrationUseCase.validEmail(state.email)) {
                    state = state.copy(
                        emailError = true
                    )
                }
                if (!registrationUseCase.validPassword(state.password)) {
                    state = state.copy(
                        passwordError = true
                    )
                }
                if (!state.nameError && !state.emailError && !state.passwordError) {
                    submit(state.name, state.email, state.password)
                }
            }
        }
    }

    private fun submit(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(name, email, password).onSuccess {
                println(it)
            }.onFailure {
                println(it)
            }
        }
    }
}
