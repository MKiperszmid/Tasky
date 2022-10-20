package com.mk.tasky.agenda.domain.model

import com.mk.tasky.core.util.AgendaItemType

data class SyncItem(
    val id: String,
    val type: SyncType,
    val agendaItemType: AgendaItemType
)
