package com.mk.tasky.agenda.detail.reminder.domain.usecase

import com.mk.tasky.agenda.domain.usecase.DeleteReminder

data class ReminderUseCases(
    val getReminder: GetReminder,
    val saveReminder: SaveReminder,
    val deleteReminder: DeleteReminder
)
