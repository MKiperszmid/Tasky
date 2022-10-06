package com.mk.tasky.agenda.presentation.detail.event

import android.net.Uri
import com.mk.tasky.agenda.domain.model.AgendaPhoto
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailEventEvents {
    object OnEdit : DetailEventEvents()
    object OnSave : DetailEventEvents()
    object OnNotificationReminderClick : DetailEventEvents()
    object OnNotificationReminderDismiss : DetailEventEvents()
    data class OnNotificationReminderSelect(val reminderType: NotificationTypes) :
        DetailEventEvents()

    object OnEventDelete : DetailEventEvents()
    data class OnUpdatedInformation(val title: String, val description: String) :
        DetailEventEvents()

    data class OnFromDateSelected(val date: LocalDate) : DetailEventEvents()
    data class OnFromTimeSelected(val time: LocalTime) : DetailEventEvents()

    data class OnToDateSelected(val date: LocalDate) : DetailEventEvents()
    data class OnToTimeSelected(val time: LocalTime) : DetailEventEvents()

    data class OnAddPhoto(val photo: AgendaPhoto) : DetailEventEvents()
    data class DeletePhoto(val location: Uri) : DetailEventEvents()
    data class OnFilterTypeSelect(val type: DetailEventFilterType) : DetailEventEvents()

    object OnOpenDialog : DetailEventEvents()
    object OnCloseDialog : DetailEventEvents()
    data class OnValueChangeDialog(val email: String) : DetailEventEvents()
    data class OnAddVisitor(val email: String) : DetailEventEvents()
}
