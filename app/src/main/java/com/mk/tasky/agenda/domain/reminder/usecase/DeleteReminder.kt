package com.mk.tasky.agenda.domain.reminder.usecase

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class DeleteReminder(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteReminderById(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }
}
