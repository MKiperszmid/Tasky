package com.mk.tasky.agenda.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mk.tasky.agenda.data.local.entity.EventEntity
import com.mk.tasky.agenda.data.local.entity.PhotoEntity

data class EventWithPhotos(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "eventId"
    )
    val photos: List<PhotoEntity>
)
