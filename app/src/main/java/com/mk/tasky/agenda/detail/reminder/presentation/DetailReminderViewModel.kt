package com.mk.tasky.agenda.detail.reminder.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailReminderState())
        private set

    init {
        savedStateHandle.get<String>("date")?.let {
            state = state.copy(
                date = LocalDateTime.parse(it)
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
                var updatedDate = state.date
                updatedDate = updatedDate.withDayOfYear(event.date.dayOfYear)
                updatedDate = updatedDate.withMonth(event.date.monthValue)
                updatedDate = updatedDate.withYear(event.date.year)
                state = state.copy(
                    date = updatedDate
                )
            }
            is DetailReminderEvent.OnTimeSelected -> {
                var updatedTime = state.date
                updatedTime = updatedTime.withHour(event.time.hour)
                updatedTime = updatedTime.withMinute(event.time.minute)
                state = state.copy(
                    date = updatedTime
                )
            }
        }
    }
}
