package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mk.tasky.agenda.domain.model.SyncType
import com.mk.tasky.core.util.AgendaItemType

@Entity
data class SyncEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val type: SyncType,
    val agendaItemType: AgendaItemType
)
