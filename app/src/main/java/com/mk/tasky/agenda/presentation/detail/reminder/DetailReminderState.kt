package com.mk.tasky.agenda.presentation.detail.reminder

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

data class DetailReminderState(
    val isEditing: Boolean = true,
    val id: String? = null,
    val title: String = "New Reminder",
    val description: String = "Description",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val reminder: NotificationTypes = NotificationTypes.THIRTY_MINUTES,
    val isSelectingNotificationReminder: Boolean = false,
    val shouldExit: Boolean = false
)
