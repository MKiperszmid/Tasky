package com.mk.tasky.agenda.domain.usecase.reminder

data class ReminderUseCases(
    val getReminder: GetReminder,
    val saveReminder: SaveReminder,
    val deleteReminder: DeleteReminder
)
