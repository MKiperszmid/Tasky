package com.mk.tasky.agenda.detail.reminder.presentation

import com.mk.tasky.agenda.detail.components.model.ReminderTypes

sealed class DetailReminderEvent {
    object OnEdit : DetailReminderEvent()
    object OnSave : DetailReminderEvent()
    object OnNotificationReminderClick : DetailReminderEvent()
    object OnNotificationReminderDismiss : DetailReminderEvent()
    data class OnNotificationReminderSelect(val reminderType: ReminderTypes) : DetailReminderEvent()
    object OnReminderDelete : DetailReminderEvent()
}
