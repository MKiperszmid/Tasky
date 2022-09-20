package com.mk.tasky.agenda.presentation.detail.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.tasky.agenda.domain.usecase.task.TaskUseCases
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.agenda.presentation.home.HomeItemOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskUseCases: TaskUseCases
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
            viewModelScope.launch {
                val task = taskUseCases.getTask(itemId)
                state = state.copy(
                    id = itemId,
                    title = task.title,
                    description = task.description,
                    date = task.taskDateTime.toLocalDate(),
                    time = task.taskDateTime.toLocalTime(),
                    notificationType = NotificationTypes.from(task.taskDateTime, task.remindAt),
                    isDone = task.isDone
                )
            }
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
                    HomeItemOptions.DELETE -> {
                        viewModelScope.launch {
                            taskUseCases.deleteTask(itemId)
                            state = state.copy(
                                shouldExit = true
                            )
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    fun onEvent(event: DetailTaskEvent) {
        when (event) {
            is DetailTaskEvent.OnDateSelected -> {
                state = state.copy(
                    date = event.date
                )
            }
            DetailTaskEvent.OnEdit -> {
                state = state.copy(
                    isEditing = true
                )
            }
            DetailTaskEvent.OnTaskDelete -> {
                state.id?.let {
                    viewModelScope.launch(NonCancellable) {
                        taskUseCases.deleteTask(it)
                    }
                }
                state = state.copy(
                    shouldExit = true
                )
            }
            DetailTaskEvent.OnSave -> {
                state = state.copy(
                    isEditing = false
                )
                viewModelScope.launch {
                    taskUseCases.saveTask(
                        id = state.id,
                        title = state.title,
                        description = state.description,
                        time = state.time,
                        date = state.date,
                        reminder = state.notificationType,
                        isDone = state.isDone
                    )
                    state = state.copy(
                        shouldExit = true
                    )
                }
            }
            is DetailTaskEvent.OnTimeSelected -> {
                state = state.copy(
                    time = event.time
                )
            }
            is DetailTaskEvent.OnUpdatedInformation -> {
                if (event.title.isNotBlank()) {
                    state = state.copy(
                        title = event.title
                    )
                }
                if (event.description.isNotBlank()) {
                    state = state.copy(
                        description = event.description
                    )
                }
            }
            DetailTaskEvent.OnNotificationSelectorClick -> {
                state = state.copy(
                    showDropdown = true
                )
            }
            DetailTaskEvent.OnNotificationSelectorDismiss -> {
                state = state.copy(
                    showDropdown = false
                )
            }
            is DetailTaskEvent.OnNotificationSelectorSelect -> {
                state = state.copy(notificationType = event.notificationType)
            }
        }
    }
}
