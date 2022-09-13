package com.mk.tasky.agenda.presentation.detail.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mk.tasky.agenda.domain.usecase.task.TaskUseCases
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import java.time.LocalDateTime
import javax.inject.Inject

class DetailTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailTaskState())
        private set

    init {
        savedStateHandle.get<String>("date")?.let {
            state = state.copy(
                date = LocalDateTime.parse(it).toLocalDate(),
                time = LocalDateTime.parse(it).toLocalTime()
            )
        }

        val itemId = savedStateHandle.get<String>("id")
        if (itemId != null) {
            // TODO: Get Tasks from backend
            savedStateHandle.get<String>("action")?.let {
                when (HomeItemOptions.from(it)) {
                    HomeItemOptions.EDIT -> {
                        state = state.copy(
                            isEditing = true
                        )
                    }
                    HomeItemOptions.OPEN -> {
                        state = state.copy(
                            isEditing = false
                        )
                    }
                    HomeItemOptions.DELETE -> {}
                    else -> Unit
                }
            }
        }
    }

    fun onEvent(event: DetailTaskEvent) {
        when (event) {
            is DetailTaskEvent.OnDateSelected -> {}
            DetailTaskEvent.OnEdit -> {}
            DetailTaskEvent.OnTaskDelete -> {}
            DetailTaskEvent.OnSave -> {}
            is DetailTaskEvent.OnTimeSelected -> {}
            is DetailTaskEvent.OnUpdatedInformation -> {}
            DetailTaskEvent.OnNotificationSelectorClick -> {}
            DetailTaskEvent.OnNotificationSelectorDismiss -> {}
            is DetailTaskEvent.OnNotificationSelectorSelect -> {}
        }
    }
}
