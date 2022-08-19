package com.mk.tasky.authentication.registration.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.core.domain.repository.TaskyRepository
import com.mk.tasky.authentication.domain.usecase.FormValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: TaskyRepository,
    private val formValidator: FormValidatorUseCase
) : ViewModel() {
    var state by mutableStateOf(RegistrationState())
        private set

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnNameChange -> {
                state = state.copy(
                    name = event.name,
                    nameError = false,
                    validName = formValidator.validFullname(event.name)
                )
            }

            is RegistrationEvent.OnEmailChange -> {
                state = state.copy(
                    email = event.email,
                    emailError = false,
                    validEmail = formValidator.validEmail(event.email)
                )
            }
            is RegistrationEvent.OnPasswordChange -> {
                state = state.copy(
                    password = event.password,
                    passwordError = false
                )
            }
            is RegistrationEvent.TogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordHidden = !state.isPasswordHidden
                )
            }
            is RegistrationEvent.Submit -> {
                if (!formValidator.validFullname(state.name)) {
                    state = state.copy(
                        nameError = true
                    )
                }
                if (!formValidator.validEmail(state.email)) {
                    state = state.copy(
                        emailError = true
                    )
                }
                if (!formValidator.validPassword(state.password)) {
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
