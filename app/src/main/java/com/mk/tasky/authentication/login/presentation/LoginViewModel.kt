package com.mk.tasky.authentication.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            try {
                repository.login("mail@pl-coding.coxm", "Test12345").onSuccess {
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
