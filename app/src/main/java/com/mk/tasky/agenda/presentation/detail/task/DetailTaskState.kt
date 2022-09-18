package com.mk.tasky.agenda.presentation.detail.task

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

data class DetailTaskState(
    val isEditing: Boolean = true,
    val id: String? = null,
    val title: String = "New Task",
    val description: String = "Description",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val notificationType: NotificationTypes = NotificationTypes.THIRTY_MINUTES,
    val isDone: Boolean = false,
    val showDropdown: Boolean = false,
    val shouldExit: Boolean = false
)
