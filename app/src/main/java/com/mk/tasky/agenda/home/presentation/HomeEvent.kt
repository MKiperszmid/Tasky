package com.mk.tasky.agenda.home.presentation

sealed class HomeEvent {
    data class OnDayClick(val day: Int) : HomeEvent()
    object OnAgendaItemDismiss : HomeEvent()
    object OnAddAgendaClick : HomeEvent()
    object OnItemOptionsDismiss : HomeEvent()
    data class OnItemOptionsClick(val itemId: String) : HomeEvent()
}
