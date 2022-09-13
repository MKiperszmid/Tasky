package com.mk.tasky.agenda.domain.usecase.task

import com.mk.tasky.agenda.domain.repository.AgendaRepository

class ChangeStatusTask(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(id: String, isDone: Boolean) {
        repository.changeStatusTask(id, isDone)
    }
}
