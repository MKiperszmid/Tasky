package com.mk.tasky

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.core.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            repository.authenticate().onSuccess {
                state = state.copy(
                    isLoggedIn = true,
                    isLoading = false
                )
            }.onFailure {
                state = state.copy(
                    isLoggedIn = false,
                    isLoading = false
                )
            }
        }
    }
}
