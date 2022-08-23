package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDateTime

data class DetailReminderState(
    val isEditing: Boolean = true,
    val date: LocalDateTime = LocalDateTime.now(),
    val title: String = "New reminder",
    val description: String = "Description",
    val showDropdown: Boolean = false,
    val selectedReminder: ReminderTypes = ReminderTypes.THIRTY_MINUTES
)
