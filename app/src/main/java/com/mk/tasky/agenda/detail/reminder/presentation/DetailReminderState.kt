package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDateTime

data class DetailReminderState(
    val isEditing: Boolean = false,
    val date: LocalDateTime = LocalDateTime.now(),
    val title: String = "Title",
    val description: String = "Description",
    val reminderTypes: List<ReminderTypes> = listOf(
        ReminderTypes.TenMinutes,
        ReminderTypes.ThirtyMinutes,
        ReminderTypes.OneHour,
        ReminderTypes.SixHours,
        ReminderTypes.OneDay
    ),
    val showDropdown: Boolean = false,
    val selectedReminder: ReminderTypes = ReminderTypes.ThirtyMinutes
)
