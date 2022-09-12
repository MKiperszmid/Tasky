package com.mk.tasky.agenda.presentation.detail.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class DetailTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailTaskState())
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
                /*val reminder = reminderUseCases.getReminder(itemId)
                state = state.copy(
                    id = itemId,
                    title = reminder.title,
                    description = reminder.description,
                    date = reminder.dateTime.toLocalDate(),
                    time = reminder.dateTime.toLocalTime(),
                    reminder = calculateRemindAtTime(reminder)
                )*/
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
                        /*viewModelScope.launch {
                            reminderUseCases.deleteReminder(itemId)
                            state = state.copy(
                                shouldExit = true
                            )
                        }*/
                    }
                    else -> Unit
                }
            }
        }
    }

    fun onEvent(event: DetailTaskEvent) {
        when (event) {
            is DetailTaskEvent.OnDateSelected -> {}
            DetailTaskEvent.OnEdit -> {}
            DetailTaskEvent.OnTaskDelete -> {}
            DetailTaskEvent.OnSave -> {}
            is DetailTaskEvent.OnTimeSelected -> {}
            is DetailTaskEvent.OnUpdatedInformation -> {}
            DetailTaskEvent.OnNotificationSelectorClick -> {}
            DetailTaskEvent.OnNotificationSelectorDismiss -> {}
            is DetailTaskEvent.OnNotificationSelectorSelect -> {}
        }
    }
}
