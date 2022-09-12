package com.mk.tasky.agenda.presentation.detail.reminder

import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailReminderEvent {
    object OnEdit : DetailReminderEvent()
    object OnSave : DetailReminderEvent()
    object OnNotificationReminderClick : DetailReminderEvent()
    object OnNotificationReminderDismiss : DetailReminderEvent()
    data class OnNotificationReminderSelect(val reminderType: NotificationTypes) : DetailReminderEvent()
    object OnReminderDelete : DetailReminderEvent()
    data class OnUpdatedInformation(val title: String, val description: String) :
        DetailReminderEvent()

    data class OnDateSelected(val date: LocalDate) : DetailReminderEvent()
    data class OnTimeSelected(val time: LocalTime) : DetailReminderEvent()
}
