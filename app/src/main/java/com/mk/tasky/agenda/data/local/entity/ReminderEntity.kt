package com.mk.tasky.agenda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey
    val id: String = "",
    val title: String,
    val description: String,
    val remindAt: String,
    val time: String
)
