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

fun AgendaItem.Reminder.toSyncItem(type: SyncType): SyncItem {
    return SyncItem(
        id = this.reminderId,
        type = type,
        agendaItemType = AgendaItemType.REMINDER
    )
}

fun AgendaItem.Event.toSyncItem(type: SyncType): SyncItem {
    return SyncItem(
        id = this.eventId,
        type = type,
        agendaItemType = AgendaItemType.EVENT
    )
}

fun AgendaItem.Task.toSyncItem(type: SyncType): SyncItem {
    return SyncItem(
        id = this.taskId,
        type = type,
        agendaItemType = AgendaItemType.TASK
    )
}
