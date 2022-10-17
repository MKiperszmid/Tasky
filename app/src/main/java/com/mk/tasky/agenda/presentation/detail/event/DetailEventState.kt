package com.mk.tasky.agenda.presentation.detail.event

import com.mk.tasky.agenda.domain.model.AgendaPhoto
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalDateTime
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
    val photos: List<AgendaPhoto> = emptyList(),
    val reminder: NotificationTypes = NotificationTypes.THIRTY_MINUTES,
    val isSelectingNotificationReminder: Boolean = false,
    val shouldExit: Boolean = false,
    val selectedFilterType: DetailEventFilterType = DetailEventFilterType.ALL,
    val attendees: List<Attendee> = emptyList(),
    val hostId: String = "",
    val isHost: Boolean = true,
    val showDialog: Boolean = false,
    val isValidDialog: Boolean = false,
    val isErrorDialog: Boolean = false,
    val dialogEmail: String = "",
    val isLoadingDialog: Boolean = false
)

