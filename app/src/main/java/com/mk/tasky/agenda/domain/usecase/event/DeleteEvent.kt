package com.mk.tasky.agenda.domain.usecase.event

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class DeleteEvent(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteEventById(id)
    }
}
