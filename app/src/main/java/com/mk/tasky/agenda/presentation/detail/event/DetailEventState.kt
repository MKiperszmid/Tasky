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
    val attendees: List<Attendee> = defaultList, // emptyList()
    val hostId: String = "", // TODO: Update with hostId
    val isHost: Boolean = true // TODO: Update so it changes value based on hostId
)

private val defaultList = listOf(
    Attendee(
        "ms@asd.com",
        "Michael Scott",
        "1",
        "123",
        true,
        LocalDateTime.now()
    ),
    Attendee(
        "ds@asd.com",
        "Dwyght Schrute",
        "2",
        "123",
        true,
        LocalDateTime.now()
    ),
    Attendee(
        "jh@asd.com",
        "Jim Halpert",
        "3",
        "123",
        false,
        LocalDateTime.now()
    )
)
