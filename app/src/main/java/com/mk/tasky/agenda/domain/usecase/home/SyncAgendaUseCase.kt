package com.mk.tasky.agenda.domain.usecase.home

import com.mk.tasky.agenda.domain.sync.AgendaSync
import java.util.*

class SyncAgendaUseCase(
    private val agendaSync: AgendaSync
) {
    fun syncRemoteWithLocal(): UUID {
        return agendaSync.syncAgendaRemoteWithLocal()
    }

    fun syncLocalWithRemote(): UUID {
        return agendaSync.syncAgendaLocalWithRemote()
    }
}
