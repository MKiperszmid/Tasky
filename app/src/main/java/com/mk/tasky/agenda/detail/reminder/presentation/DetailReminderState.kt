package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import com.mk.tasky.agenda.detail.reminder.presentation.model.DetailRegistrationInformation
import java.time.LocalDateTime

data class DetailReminderState(
    val isEditing: Boolean = true,
    val information: DetailRegistrationInformation = DetailRegistrationInformation(
        title = "New reminder",
        description = "Description",
        date = LocalDateTime.now(),
        reminder = ReminderTypes.THIRTY_MINUTES
    ),
    val editableInformation: DetailRegistrationInformation = information.copy(),
    val showDropdown: Boolean = false
)
