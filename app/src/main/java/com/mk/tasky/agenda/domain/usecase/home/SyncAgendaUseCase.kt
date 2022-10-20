package com.mk.tasky.agenda.domain.usecase.home

import com.mk.tasky.agenda.domain.sync.AgendaSync

class SyncAgendaUseCase(
    private val agendaSync: AgendaSync
) {
    suspend operator fun invoke() {
        agendaSync.syncAgenda()
    }
}
