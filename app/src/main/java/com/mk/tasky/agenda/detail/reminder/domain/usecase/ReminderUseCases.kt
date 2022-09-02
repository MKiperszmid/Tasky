package com.mk.tasky.agenda.detail.reminder.domain.usecase

data class ReminderUseCases(
    val getReminder: GetReminder,
    val saveReminder: SaveReminder,
    val deleteReminder: DeleteReminder
)
