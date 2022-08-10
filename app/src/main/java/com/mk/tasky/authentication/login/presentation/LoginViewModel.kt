package com.mk.tasky.authentication.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.login.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.login("mail@pl-coding.com", "Test12345").onSuccess {
                println(it)
            }.onFailure {
                println(it)
            }
        }
    }
}
