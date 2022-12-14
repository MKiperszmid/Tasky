package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val dateTime: Long
)
