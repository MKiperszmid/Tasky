package com.mk.tasky.agenda.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mk.tasky.agenda.home.domain.usecase.FormatNameUseCase
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val formatNameUseCase: FormatNameUseCase
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        val user = preferences.loadLoggedUser()!! // Can't be null if we get to the Home Screen
        state = state.copy(
            profileName = formatNameUseCase(user.fullName)
        )
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnDayClick -> {
                state = state.copy(
                    selectedDay = event.day
                )
            }
        }
    }
}
