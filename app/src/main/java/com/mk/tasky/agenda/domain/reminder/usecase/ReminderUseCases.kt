package com.mk.tasky.agenda.domain.reminder.usecase

data class ReminderUseCases(
    val getReminder: GetReminder,
    val saveReminder: SaveReminder,
    val deleteReminder: DeleteReminder
)
