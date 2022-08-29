package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ReminderEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val remindAt: Long,
    val day: Int,
    val month: Int,
    val year: Int,
    val hour: Int,
    val minute: Int
)
