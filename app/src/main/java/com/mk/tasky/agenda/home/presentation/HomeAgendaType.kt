package com.mk.tasky.agenda.home.presentation

sealed class HomeAgendaType(val name: String) {
    object Event : HomeAgendaType("Event")
    object Task : HomeAgendaType("Task")
    object Reminder : HomeAgendaType("Reminder")
}
