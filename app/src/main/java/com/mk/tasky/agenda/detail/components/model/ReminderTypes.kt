package com.mk.tasky.agenda.detail.components.model

sealed class ReminderTypes(val type: String) {
    object TenMinutes : ReminderTypes("10 minutes before")
    object ThirtyMinutes : ReminderTypes("30 minutes before")
    object OneHour : ReminderTypes("1 hour before")
    object SixHours : ReminderTypes("6 hours before")
    object OneDay : ReminderTypes("1 day before")
}
