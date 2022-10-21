package com.mk.tasky.agenda.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mk.tasky.agenda.data.remote.worker.SyncWorker
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.usecase.home.FormatNameUseCase
import com.mk.tasky.agenda.domain.usecase.home.HomeUseCases
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val formatNameUseCase: FormatNameUseCase,
    private val repository: AgendaRepository,
    private val homeUseCases: HomeUseCases,
    private val workManager: WorkManager
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private lateinit var syncObserver: Observer<WorkInfo>
    private lateinit var syncStateInfo: LiveData<WorkInfo>

    init {
        val user = preferences.loadLoggedUser()!! // Can't be null if we get to the Home Screen
        state = state.copy(
            profileName = formatNameUseCase(user.fullName)
        )
        getAgendaForSelectedDate(forceRemote = false)

        viewModelScope.launch {
            val id = homeUseCases.syncAgendaUseCase()
            runSyncObserver(id)
        }
    }

    private fun runSyncObserver(id: UUID) {
        syncObserver = Observer {
            if (it.state == WorkInfo.State.SUCCEEDED) {
                getAgendaForSelectedDate(forceRemote = true)
            }
        }
        syncStateInfo = workManager.getWorkInfoByIdLiveData(id)
        syncStateInfo.observeForever(syncObserver)
    }

    override fun onCleared() {
        super.onCleared()
        syncStateInfo.removeObserver(syncObserver)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnDayClick -> {
                state = state.copy(
                    selectedDay = event.day
                )
                getAgendaForSelectedDate(forceRemote = true)
            }
            is HomeEvent.OnAgendaItemDismiss -> {
                state = state.copy(
                    showAgendaOptions = false
                )
            }
            is HomeEvent.OnAddAgendaClick -> {
                state = state.copy(
                    showAgendaOptions = true
                )
            }
            is HomeEvent.OnItemOptionsClick -> {
                state = state.copy(
                    selectedAgendaItem = event.agendaItem,
                    showItemOptions = true
                )
            }
            HomeEvent.OnItemOptionsDismiss -> {
                state = state.copy(
                    showItemOptions = false
                )
            }
            HomeEvent.OnLogoutClick -> {
                state = state.copy(
                    showLogout = true
                )
            }
            HomeEvent.OnLogoutDismiss -> {
                state = state.copy(
                    showLogout = false
                )
            }
            HomeEvent.OnRefreshAgenda -> {
                getAgendaForSelectedDate(forceRemote = false)
            }
            is HomeEvent.OnDeleteItem -> {
                viewModelScope.launch {
                    when (event.agendaItem) {
                        is AgendaItem.Reminder -> homeUseCases.deleteReminder(event.agendaItem.id)
                        is AgendaItem.Task -> homeUseCases.deleteTask(event.agendaItem.id)
                        is AgendaItem.Event -> homeUseCases.deleteEvent(event.agendaItem.id)
                    }
                    getAgendaForSelectedDate(forceRemote = false)
                }
            }
            is HomeEvent.OnDateSelected -> {
                state = state.copy(
                    currentDate = event.date,
                    selectedDay = 0
                )
                getAgendaForSelectedDate(forceRemote = true)
            }
            is HomeEvent.OnItemClick -> {
                viewModelScope.launch {
                    if (event.agendaItem is AgendaItem.Task) {
                        homeUseCases.changeStatusTask(event.agendaItem.id, !event.agendaItem.isDone)
                        getAgendaForSelectedDate(forceRemote = false)
                    }
                }
            }
        }
    }

    private fun getAgendaForSelectedDate(forceRemote: Boolean) {
        viewModelScope.launch {
            repository.getAgenda(
                state.currentDate.plusDays(state.selectedDay.toLong()),
                forceRemote
            ).collect { agenda ->
                state = state.copy(agendaItems = agenda.items.sortedBy { it.time })
            }
        }
    }
}
