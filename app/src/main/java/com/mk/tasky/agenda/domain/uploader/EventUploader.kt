package com.mk.tasky.agenda.domain.uploader

import com.mk.tasky.agenda.domain.model.AgendaItem

interface EventUploader {
    suspend fun uploadEvent(event: AgendaItem.Event, isEdit: Boolean)
}
