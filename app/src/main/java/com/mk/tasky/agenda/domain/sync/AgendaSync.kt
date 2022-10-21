package com.mk.tasky.agenda.domain.sync

import java.util.*

interface AgendaSync {
    suspend fun syncAgenda(): UUID
}
