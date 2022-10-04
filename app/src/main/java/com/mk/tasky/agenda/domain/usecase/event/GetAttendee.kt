package com.mk.tasky.agenda.domain.usecase.event

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class GetAttendee(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(email: String) = repository.getAttendee(email)
}
