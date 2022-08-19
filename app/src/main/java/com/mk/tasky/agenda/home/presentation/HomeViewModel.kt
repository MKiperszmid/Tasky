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
        state = state.copy(
            profileName = formatNameUseCase(
                preferences.loadLoggedUser().fullName
                    ?: "AA"
            ) // TODO: Investigate how to remove null from SharedPrefs
        )
        println()
    }
}
