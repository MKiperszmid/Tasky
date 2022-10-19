package com.mk.tasky.agenda.domain.usecase.event

import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.AgendaPhoto
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.uploader.EventUploder
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class SaveEvent(
    private val repository: AgendaRepository,
    private val eventUploader: EventUploder
) {
    suspend operator fun invoke(
        id: String?,
        title: String,
        description: String,
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime,
        attendees: List<Attendee>,
        hostId: String,
        isHost: Boolean,
        photos: List<AgendaPhoto>,
        reminder: NotificationTypes
    ) {
        val fromDateTime = LocalDateTime.of(fromDate, fromTime)
        val toDateTime = LocalDateTime.of(toDate, toTime)
        val remindAtTime = NotificationTypes.remindAt(fromDateTime, reminder)
        val isEdit = id != null
        val agendaEvent = AgendaItem.Event(
            eventId = id ?: UUID.randomUUID().toString(),
            eventTitle = title,
            eventDescription = description,
            eventFromDateTime = fromDateTime,
            eventToDateTime = toDateTime,
            eventRemindAt = remindAtTime,
            attendees = attendees,
            hostId = hostId,
            isHost = isHost,
            photos = photos
        )
        repository.insertEvent(agendaEvent, isEdit)
        eventUploader.uploadEvent(agendaEvent, isEdit)
    }
}
