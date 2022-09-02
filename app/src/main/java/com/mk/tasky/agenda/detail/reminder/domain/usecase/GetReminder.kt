package com.mk.tasky.agenda.detail.reminder.domain.usecase

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class GetReminder(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) = repository.getReminderById(id)
}
