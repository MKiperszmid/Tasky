package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    val eventId: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val fromDateTime: Long,
    val toDateTime: Long,
    val hostId: String,
    val isHost: Boolean
)
