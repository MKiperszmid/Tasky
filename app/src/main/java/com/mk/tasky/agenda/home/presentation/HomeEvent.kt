package com.mk.tasky.agenda.home.presentation

sealed class HomeEvent {
    data class OnDayClick(val day: Int) : HomeEvent()
}
