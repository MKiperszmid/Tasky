package com.mk.tasky.agenda.domain.usecase.task

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class GetTask(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) = repository.getTaskById(id)
}
