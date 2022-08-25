package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import java.time.LocalDate
import java.time.LocalTime

sealed class DetailReminderEvent {
    object OnEdit : DetailReminderEvent()
    object OnSave : DetailReminderEvent()
    object OnNotificationReminderClick : DetailReminderEvent()
    object OnNotificationReminderDismiss : DetailReminderEvent()
    data class OnNotificationReminderSelect(val reminderType: ReminderTypes) : DetailReminderEvent()
    object OnReminderDelete : DetailReminderEvent()
    data class OnUpdatedInformation(val title: String, val description: String) :
        DetailReminderEvent()

    data class OnDateSelected(val date: LocalDate) : DetailReminderEvent()
    data class OnTimeSelected(val time: LocalTime) : DetailReminderEvent()
}
