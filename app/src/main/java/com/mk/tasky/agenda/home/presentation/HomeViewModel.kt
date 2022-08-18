package com.mk.tasky.agenda.home.presentation

import androidx.lifecycle.ViewModel
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {
    init {
        println(preferences.loadLoggedUser())
    }
}
