package com.mk.tasky.agenda.domain.sync

interface AgendaSync {
    suspend fun syncAgenda()
}
