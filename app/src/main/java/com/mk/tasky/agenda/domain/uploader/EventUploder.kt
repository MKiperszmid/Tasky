package com.mk.tasky.agenda.domain.uploader

import com.mk.tasky.agenda.domain.model.AgendaItem

interface EventUploder {
    suspend fun uploadEvent(event: AgendaItem.Event, isEdit: Boolean)
}
