package com.mk.tasky.agenda.domain.usecase.home

import com.mk.tasky.agenda.domain.usecase.event.DeleteEvent
import com.mk.tasky.agenda.domain.usecase.reminder.DeleteReminder
import com.mk.tasky.agenda.domain.usecase.task.ChangeStatusTask
import com.mk.tasky.agenda.domain.usecase.task.DeleteTask

data class HomeUseCases(
    val deleteReminder: DeleteReminder,
    val deleteTask: DeleteTask,
    val deleteEvent: DeleteEvent,
    val changeStatusTask: ChangeStatusTask,
    val syncAgendaUseCase: SyncAgendaUseCase
)
