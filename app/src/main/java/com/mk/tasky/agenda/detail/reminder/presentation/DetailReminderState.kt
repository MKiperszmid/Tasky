package com.mk.tasky.agenda.detail.reminder.presentation

import java.time.LocalDateTime

data class DetailReminderState(
    val isEditing: Boolean = false,
    val date: LocalDateTime = LocalDateTime.now(),
    val title: String = "Title"
)
