package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttendeeEntity(
    @PrimaryKey
    val attendeeId: String,
    val fullName: String,
    val userId: String,
    val isGoing: Boolean,
    val email: String,
    val remindAt: Long
)