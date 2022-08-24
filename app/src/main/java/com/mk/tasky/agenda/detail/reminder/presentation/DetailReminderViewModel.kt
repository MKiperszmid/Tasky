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
                information = state.information.copy(date = LocalDateTime.parse(it)),
                editableInformation = state.editableInformation.copy(date = LocalDateTime.parse(it))
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
                    isEditing = false,
                    information = state.editableInformation.copy()
                )
            }
            is DetailReminderEvent.OnNotificationReminderSelect -> {
                state = state.copy(
                    editableInformation = state.editableInformation.copy(reminder = event.reminderType)
                )
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
            DetailReminderEvent.OnCancelEdit -> {
                state = state.copy(
                    editableInformation = state.information.copy(),
                    isEditing = false
                )
            }
            is DetailReminderEvent.OnUpdatedInformation -> {
                if (event.title != "") {
                    state = state.copy(
                        editableInformation = state.editableInformation.copy(title = event.title)
                    )
                }
                if (event.description != "") {
                    state = state.copy(
                        editableInformation = state.editableInformation.copy(description = event.description)
                    )
                }
            }
        }
    }
}
