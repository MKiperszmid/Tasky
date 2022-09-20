package com.mk.tasky.agenda.domain.usecase.home

import com.mk.tasky.agenda.domain.usecase.reminder.DeleteReminder
import com.mk.tasky.agenda.domain.usecase.task.ChangeStatusTask
import com.mk.tasky.agenda.domain.usecase.task.DeleteTask

data class HomeUseCases(
    val deleteReminder: DeleteReminder,
    val deleteTask: DeleteTask,
    val changeStatusTask: ChangeStatusTask
)
