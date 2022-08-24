package com.mk.tasky.agenda.detail.reminder.presentation.model

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDateTime

data class DetailRegistrationInformation(
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val reminder: ReminderTypes
)
