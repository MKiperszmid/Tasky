package com.mk.tasky.agenda.domain.usecase.home

import com.mk.tasky.agenda.domain.sync.AgendaSync
import java.util.*

class SyncAgendaUseCase(
    private val agendaSync: AgendaSync
) {
    suspend operator fun invoke(): UUID {
        return agendaSync.syncAgenda()
    }
}
