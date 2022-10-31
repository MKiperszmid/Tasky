package com.mk.tasky.agenda.domain.sync

import java.util.*

interface AgendaSync {
    fun syncAgendaRemoteWithLocal(): UUID
    fun syncAgendaLocalWithRemote(): UUID
}
