package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDateTime

data class DetailReminderState(
    val isEditing: Boolean = true,
    val title: String = "New Reminder",
    val description: String = "Description",
    val date: LocalDateTime = LocalDateTime.now(),
    val reminder: ReminderTypes = ReminderTypes.THIRTY_MINUTES,
    val showDropdown: Boolean = false
)
