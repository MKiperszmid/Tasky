package com.mk.tasky.agenda.detail.reminder.presentation

sealed class DetailReminderEvent {
    object OnEdit : DetailReminderEvent()
    object OnSave : DetailReminderEvent()
}
