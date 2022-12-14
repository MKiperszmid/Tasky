package com.mk.tasky.agenda.presentation.detail.reminder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.domain.usecase.reminder.ReminderUseCases
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reminderUseCases: ReminderUseCases
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

        val itemId = savedStateHandle.get<String>("id")
        if (itemId != null) {
            viewModelScope.launch {
                val reminder = reminderUseCases.getReminder(itemId)
                state = state.copy(
                    id = itemId,
                    title = reminder.title,
                    description = reminder.description,
                    date = reminder.reminderDateTime.toLocalDate(),
                    time = reminder.reminderDateTime.toLocalTime(),
                    reminder = NotificationTypes.from(reminder.reminderDateTime, reminder.remindAt)
                )
            }
            savedStateHandle.get<String>("action")?.let {
                when (HomeItemOptions.from(it)) {
                    HomeItemOptions.EDIT -> {
                        state = state.copy(
                            isEditing = true
                        )
                    }
                    HomeItemOptions.OPEN -> {
                        state = state.copy(
                            isEditing = false
                        )
                    }
                    HomeItemOptions.DELETE -> {
                        viewModelScope.launch {
                            reminderUseCases.deleteReminder(itemId)
                            state = state.copy(
                                shouldExit = true
                            )
                        }
                    }
                    else -> Unit
                }
            }
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
                    reminderUseCases.saveReminder(
                        id = state.id,
                        title = state.title,
                        description = state.description,
                        time = state.time,
                        date = state.date,
                        reminder = state.reminder
                    )
                    state = state.copy(
                        shouldExit = true
                    )
                }
            }
            is DetailReminderEvent.OnNotificationReminderSelect -> {
                state = state.copy(reminder = event.reminderType)
            }
            DetailReminderEvent.OnNotificationReminderDismiss -> {
                state = state.copy(
                    isSelectingNotificationReminder = false
                )
            }
            DetailReminderEvent.OnNotificationReminderClick -> {
                state = state.copy(
                    isSelectingNotificationReminder = true
                )
            }
            DetailReminderEvent.OnReminderDelete -> {
                state.id?.let {
                    viewModelScope.launch(NonCancellable) {
                        reminderUseCases.deleteReminder(it)
                    }
                }
                state = state.copy(
                    shouldExit = true
                )
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
