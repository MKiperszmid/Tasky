package com.mk.tasky.agenda.presentation.detail.event

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

data class DetailEventState(
    val isEditing: Boolean = true,
    val id: String? = null,
    val title: String = "New Event",
    val description: String = "Description",
    val fromDate: LocalDate = LocalDate.now(),
    val fromTime: LocalTime = LocalTime.now(),
    val toDate: LocalDate = LocalDate.now(),
    val toTime: LocalTime = LocalTime.now(),
    val reminder: NotificationTypes = NotificationTypes.THIRTY_MINUTES,
    val isSelectingNotificationReminder: Boolean = false,
    val shouldExit: Boolean = false
)
