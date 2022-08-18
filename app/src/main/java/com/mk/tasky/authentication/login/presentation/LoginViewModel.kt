package com.mk.tasky.authentication.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.data.remote.exceptions.LoginException
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.domain.usecase.FormValidatorUseCase
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val formValidator: FormValidatorUseCase,
    private val preferences: Preferences
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                state = state.copy(
                    email = event.email,
                    emailError = false,
                    validEmail = formValidator.validEmail(event.email)
                )
            }
            is LoginEvent.OnPasswordChange -> {
                state = state.copy(
                    password = event.password,
                    passwordError = false
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordHidden = !state.isPasswordHidden
                )
            }
            is LoginEvent.Submit -> {
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
                if (!state.emailError && !state.passwordError) {
                    submit(state.email, state.password)
                }
            }
        }
    }

    private fun submit(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).onSuccess {
                preferences.saveToken(it.token)
                preferences.saveFullName(it.fullName)
                preferences.saveUserId(it.userId)
                state = state.copy(
                    isLoggedIn = true
                )
            }.onFailure {
                if (it is LoginException && it.message?.isBlank() == false) {
                    println(it.message)
                } else {
                    println(it)
                }
            }
        }
    }
}
