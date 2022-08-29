package com.mk.tasky.agenda.detail.reminder.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.detail.reminder.domain.usecase.SaveReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveReminder: SaveReminder
) : ViewModel() {
    var state by mutableStateOf(DetailReminderState())
        private set

    init {
        savedStateHandle.get<String>("date")?.let {
            state = state.copy(
                date = LocalDateTime.parse(it).toLocalDate(),
                time = LocalDateTime.parse(it).toLocalTime()
            )
        }
    }

    fun onEvent(event: DetailReminderEvent) {
        when (event) {
            DetailReminderEvent.OnEdit -> {
                state = state.copy(
                    isEditing = true
                )
            }
            DetailReminderEvent.OnSave -> {
                state = state.copy(
                    isEditing = false
                )
                viewModelScope.launch {
                    saveReminder(
                        title = state.title,
                        description = state.description,
                        time = state.time,
                        date = state.date,
                        reminder = state.reminder
                    )
                }
            }
            is DetailReminderEvent.OnNotificationReminderSelect -> {
                state = state.copy(reminder = event.reminderType)
            }
            DetailReminderEvent.OnNotificationReminderDismiss -> {
                state = state.copy(
                    showDropdown = false
                )
            }
            DetailReminderEvent.OnNotificationReminderClick -> {
                state = state.copy(
                    showDropdown = true
                )
            }
            DetailReminderEvent.OnReminderDelete -> {
                println("Delete Reminder")
            }
            is DetailReminderEvent.OnUpdatedInformation -> {
                if (event.title.isNotBlank()) {
                    state = state.copy(
                        title = event.title
                    )
                }
                if (event.description.isNotBlank()) {
                    state = state.copy(
                        description = event.description
                    )
                }
            }
            is DetailReminderEvent.OnDateSelected -> {
                state = state.copy(
                    date = event.date
                )
            }
            is DetailReminderEvent.OnTimeSelected -> {
                state = state.copy(
                    time = event.time
                )
            }
        }
    }
}
