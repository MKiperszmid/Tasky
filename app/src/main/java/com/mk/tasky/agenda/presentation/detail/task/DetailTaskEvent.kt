package com.mk.tasky.agenda.presentation.detail.task

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailTaskEvent {
    object OnEdit : DetailTaskEvent()
    object OnSave : DetailTaskEvent()
    object OnNotificationSelectorClick : DetailTaskEvent()
    object OnNotificationSelectorDismiss : DetailTaskEvent()
    data class OnNotificationSelectorSelect(val notificationType: NotificationTypes) : DetailTaskEvent()
    object OnTaskDelete : DetailTaskEvent()
    data class OnUpdatedInformation(val title: String, val description: String) :
        DetailTaskEvent()

    data class OnDateSelected(val date: LocalDate) : DetailTaskEvent()
    data class OnTimeSelected(val time: LocalTime) : DetailTaskEvent()
}
