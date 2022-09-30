package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttendeeEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val userId: String,
    val isGoing: Boolean,
    val eventId: String,
    val email: String,
    val remindAt: Long
)