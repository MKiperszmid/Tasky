package com.mk.tasky.agenda.domain.usecase.event

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class GetEvent(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) = repository.getEventById(id)
}
