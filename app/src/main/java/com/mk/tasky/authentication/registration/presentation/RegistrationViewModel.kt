package com.mk.tasky.authentication.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.authentication.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.register("Testing", "testing@mk.com", "teasASDF1").onSuccess {
                println(it)
            }.onFailure {
                println(it)
            }
        }
    }
}
