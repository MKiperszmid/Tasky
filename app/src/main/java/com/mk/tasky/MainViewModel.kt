package com.mk.tasky

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.data.local.AgendaDatabase
import com.mk.tasky.core.domain.preferences.Preferences
import com.mk.tasky.core.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val preferences: Preferences,
    private val database: AgendaDatabase,
    private val dispatcher: CoroutineDispatcher
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

    fun onLogout() {
        viewModelScope.launch {
            preferences.deleteUser()
            withContext(dispatcher) {
                database.clearAllTables()
            }
        }
    }
}
