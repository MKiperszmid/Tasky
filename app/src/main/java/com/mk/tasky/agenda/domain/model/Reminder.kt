package com.mk.tasky.agenda.domain.model

import java.time.LocalDateTime

data class Reminder(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: LocalDateTime,
    val dateTime: LocalDateTime
)
