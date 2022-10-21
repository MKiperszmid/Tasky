package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.SyncEntity
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.model.SyncItem
import com.mk.tasky.agenda.domain.model.SyncType
import com.mk.tasky.core.util.AgendaItemType

fun SyncEntity.toDomain(): SyncItem {
    return SyncItem(
        id = this.id,
        type = this.type,
        agendaItemType = this.agendaItemType
    )
}

fun SyncItem.toEntity(): SyncEntity {
    return SyncEntity(
        id = this.id,
        type = this.type,
        agendaItemType = this.agendaItemType
    )
}

fun AgendaItem.toSyncItem(type: SyncType): SyncItem {
    val agendatype = when(this) {
        is AgendaItem.Event -> AgendaItemType.EVENT
        is AgendaItem.Reminder -> AgendaItemType.REMINDER
        is AgendaItem.Task -> AgendaItemType.TASK
    }
    return SyncItem(
        id = this.id,
        type = type,
        agendaItemType = agendatype
    )
}

