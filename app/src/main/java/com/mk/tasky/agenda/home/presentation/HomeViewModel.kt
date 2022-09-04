package com.mk.tasky.agenda.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.usecase.DeleteReminder
import com.mk.tasky.agenda.home.domain.usecase.FormatNameUseCase
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val formatNameUseCase: FormatNameUseCase,
    private val repository: AgendaRepository,
    private val deleteReminder: DeleteReminder
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        val user = preferences.loadLoggedUser()!! // Can't be null if we get to the Home Screen
        state = state.copy(
            profileName = formatNameUseCase(user.fullName)
        )
        getRemindersForSelectedDate()
    }

    private fun getRemindersForSelectedDate() {
        viewModelScope.launch {
            val reminders = repository.getRemindersForDate(
                state.currentDate.toLocalDate()
                    .plusDays(state.selectedDay.toLong())
            )
            state = state.copy(
                reminders = reminders.sortedBy { it.dateTime }
            )
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnDayClick -> {
                state = state.copy(
                    selectedDay = event.day
                )
                getRemindersForSelectedDate()
            }
            is HomeEvent.OnAgendaItemDismiss -> {
                state = state.copy(
                    showAgendaOptions = false
                )
            }
            is HomeEvent.OnAddAgendaClick -> {
                state = state.copy(
                    showAgendaOptions = true
                )
            }
            is HomeEvent.OnItemOptionsClick -> {
                state = state.copy(
                    selectedItemOptionId = event.itemId,
                    showItemOptions = true
                )
            }
            HomeEvent.OnItemOptionsDismiss -> {
                state = state.copy(
                    showItemOptions = false
                )
            }
            HomeEvent.OnLogoutClick -> {
                state = state.copy(
                    showLogout = true
                )
            }
            HomeEvent.OnLogoutDismiss -> {
                state = state.copy(
                    showLogout = false
                )
            }
            HomeEvent.OnRefreshAgenda -> {
                getRemindersForSelectedDate()
            }
            is HomeEvent.OnDeleteItem -> {
                viewModelScope.launch {
                    deleteReminder(event.itemId)
                    getRemindersForSelectedDate()
                }
            }
            is HomeEvent.OnDateSelected -> {
                state = state.copy(
                    currentDate = LocalDateTime.of(event.date, LocalTime.now()),
                    selectedDay = 0
                )
                getRemindersForSelectedDate()
            }
        }
    }
}
