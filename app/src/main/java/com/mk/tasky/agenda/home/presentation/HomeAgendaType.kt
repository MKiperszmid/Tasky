package com.mk.tasky.agenda.home.presentation

sealed class HomeAgendaType(val name: String) {
    object Event : HomeAgendaType("Event")
    object Task : HomeAgendaType("Task")
    data class Reminder(val homeReminderType: HomeReminderType? = null) : HomeAgendaType("Reminder")
}

sealed class HomeReminderType(val type: String) {
    object TenMinutes : HomeReminderType("10 minutes Before")
    object ThirtyMinutes : HomeReminderType("30 minutes Before")
    object OneHour : HomeReminderType("1 hour Before")
    object SixHours : HomeReminderType("6 hours Before")
    object OneDay : HomeReminderType("1 day Before")
}
