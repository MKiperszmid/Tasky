package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class DetailReminderState(
    val isEditing: Boolean = true,
    val title: String = "New Reminder",
    val description: String = "Description",
    //val date: LocalDateTime = LocalDateTime.now(),
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val reminder: ReminderTypes = ReminderTypes.THIRTY_MINUTES,
    val showDropdown: Boolean = false
)
