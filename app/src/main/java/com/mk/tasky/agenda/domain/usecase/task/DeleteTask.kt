package com.mk.tasky.agenda.domain.usecase.task

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class DeleteTask(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteTaskById(id).onFailure {
            // TODO: Save id on db to later sync with server
        }
    }
}
