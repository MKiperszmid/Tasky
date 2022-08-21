package com.mk.tasky.agenda.home.presentation

sealed class HomeEvent {
    data class OnDayClick(val day: Int) : HomeEvent()
    data class OnAgendaItemSelected(val item: HomeAgendaType) : HomeEvent()
    object OnAgendaItemDismiss : HomeEvent()
    object OnAddAgendaClick : HomeEvent()
    object OnReminderItemDismiss : HomeEvent()
    data class OnReminderItemSelected(val item: HomeReminderType) : HomeEvent()
}
