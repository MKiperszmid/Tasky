package com.mk.tasky.agenda.presentation.detail.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.domain.usecase.event.EventUseCases
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import com.mk.tasky.core.domain.preferences.Preferences
import com.mk.tasky.core.domain.usecase.ValidateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    private val preferences: Preferences,
    private val validateEmailUseCase: ValidateEmailUseCase,
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
            viewModelScope.launch {
                val event = eventUseCases.getEvent(itemId)
                state = state.copy(
                    id = itemId,
                    title = event.title,
                    description = event.description,
                    fromDate = event.eventFromDateTime.toLocalDate(),
                    fromTime = event.eventFromDateTime.toLocalTime(),
                    toDate = event.eventToDateTime.toLocalDate(),
                    toTime = event.eventFromDateTime.toLocalTime(),
                    reminder = NotificationTypes.from(event.eventFromDateTime, event.remindAt)
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
                            // TODO: Delete
                            state = state.copy(
                                shouldExit = true
                            )
                        }
                    }
                    else -> Unit
                }
            }
        } else {
            state = state.copy(
                isHost = true,
                hostId = preferences.loadLoggedUser()?.userId!!
            )
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
                viewModelScope.launch {
                    eventUseCases.saveEvent(
                        id = state.id,
                        title = state.title,
                        description = state.description,
                        reminder = state.reminder,
                        fromDate = state.fromDate,
                        fromTime = state.fromTime,
                        toDate = state.toDate,
                        toTime = state.toTime,
                        attendees = state.attendees,
                        hostId = state.hostId,
                        isHost = state.isHost,
                        photos = state.photos
                    )
                    /*state = state.copy(
                        shouldExit = true
                    )*/
                }
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
                val deletedPhoto = state.photos.find { it.location == event.location.toString() }
                if (deletedPhoto != null) {
                    state = state.copy(
                        photos = state.photos - deletedPhoto
                    )
                }
            }
            is DetailEventEvents.OnFilterTypeSelect -> {
                state = state.copy(
                    selectedFilterType = event.type
                )
            }
            is DetailEventEvents.OnAddVisitor -> {
                if (validateEmailUseCase(state.dialogEmail)) {
                    viewModelScope.launch {
                        state = state.copy(
                            isLoadingDialog = true
                        )
                        eventUseCases.getAttendee(state.dialogEmail).onSuccess {
                            it?.let {
                                val doesAttendeeExists = state.attendees.firstOrNull { element -> element.userId == it.userId } != null
                                if (!doesAttendeeExists) {
                                    state = state.copy(
                                        attendees = state.attendees + it,
                                        showDialog = false
                                    )
                                }
                            } ?: kotlin.run {
                                // TODO: Add a snackbar saying the attendee doesn't exist
                                state = state.copy(
                                    isErrorDialog = true
                                )
                            }
                        }.onFailure {
                            // TODO: Add a snackbar with the error message
                            state = state.copy(
                                isErrorDialog = true
                            )
                        }
                        onEvent(DetailEventEvents.OnCloseDialog)
                    }
                } else {
                    state = state.copy(
                        isErrorDialog = true
                    )
                }
            }
            DetailEventEvents.OnCloseDialog -> {
                state = state.copy(
                    showDialog = false,
                    isLoadingDialog = false,
                    isErrorDialog = false,
                    dialogEmail = "",
                    isValidDialog = false
                )
            }
            DetailEventEvents.OnOpenDialog -> {
                state = state.copy(
                    showDialog = true
                )
            }
            is DetailEventEvents.OnValueChangeDialog -> {
                state = state.copy(
                    dialogEmail = event.email,
                    isErrorDialog = false,
                    isValidDialog = validateEmailUseCase(event.email)
                )
            }
        }
    }
}
