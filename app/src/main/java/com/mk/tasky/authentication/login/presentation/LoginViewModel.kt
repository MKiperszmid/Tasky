package com.mk.tasky.authentication.login.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                state = state.copy(
                    email = event.email,
                    emailError = false
                )
            }
            is LoginEvent.OnPasswordChange -> {
                state = state.copy(
                    password = event.password,
                    passwordError = false
                )
            }
            is LoginEvent.ChangePasswordVisibility -> {
                state = state.copy(
                    isPasswordHidden = !state.isPasswordHidden
                )
            }
            is LoginEvent.Submit -> {
                if (!loginUseCase.validEmail(state.email)) {
                    state = state.copy(
                        emailError = true
                    )
                }
                if (!loginUseCase.validPassword(state.password)) {
                    state = state.copy(
                        passwordError = true
                    )
                }
                if (!state.emailError && !state.passwordError) {
                    Log.d("TRACK", "Email: ${state.email} Password: ${state.password}")
                    submit(state.email, state.password)
                }
            }
        }
    }

    private fun submit(email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(email, password).onSuccess {
                    println(it)
                }.onFailure {
                    println(it)
                }
            } catch (e: Exception) {
                println()
            }
        }
    }
}
