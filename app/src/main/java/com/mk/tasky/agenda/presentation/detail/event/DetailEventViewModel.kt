package com.mk.tasky.agenda.presentation.detail.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailEventState())
        private set

    init {
        savedStateHandle.get<String>("date")?.let {
            state = state.copy(
                fromDate = LocalDateTime.parse(it).toLocalDate(),
                fromTime = LocalDateTime.parse(it).toLocalTime(),
                toDate = state.fromDate,
                toTime = state.fromTime.plusMinutes(30)
            )
        }

        val itemId = savedStateHandle.get<String>("id")
        if (itemId != null) {
            // TODO: Get Event from Repository
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
                            // TODO: Delete
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

    fun onEvent(event: DetailEventEvents) {
        when (event) {
            DetailEventEvents.OnEdit -> {
                state = state.copy(
                    isEditing = true
                )
            }
            DetailEventEvents.OnEventDelete -> {
                state.id?.let {
                    // TODO: Delete Event
                }
                state = state.copy(
                    shouldExit = true
                )
            }
            DetailEventEvents.OnNotificationReminderClick -> {
                state = state.copy(
                    isSelectingNotificationReminder = true
                )
            }
            DetailEventEvents.OnNotificationReminderDismiss -> {
                state = state.copy(
                    isSelectingNotificationReminder = false
                )
            }
            is DetailEventEvents.OnNotificationReminderSelect -> {
                state = state.copy(reminder = event.reminderType)
            }
            DetailEventEvents.OnSave -> {
                state = state.copy(
                    isEditing = false
                )
                // TODO: Save Event
            }
            is DetailEventEvents.OnFromTimeSelected -> {
                state = state.copy(
                    fromTime = event.time
                )
            }
            is DetailEventEvents.OnFromDateSelected -> {
                state = state.copy(
                    fromDate = event.date
                )
            }
            is DetailEventEvents.OnUpdatedInformation -> {
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
            is DetailEventEvents.OnToDateSelected -> {
                state = state.copy(
                    toDate = event.date
                )
            }
            is DetailEventEvents.OnToTimeSelected -> {
                state = state.copy(
                    toTime = event.time
                )
            }
            is DetailEventEvents.OnAddPhoto -> {
                if (state.photos.contains(event.photo)) return
                state = state.copy(
                    photos = state.photos + event.photo
                )
            }
            is DetailEventEvents.DeletePhoto -> {
                val deletedPhoto = state.photos.find { it.location == event.location }
                if (deletedPhoto != null) {
                    state = state.copy(
                        photos = state.photos - deletedPhoto
                    )
                }
            }
        }
    }
}
