package com.mk.tasky.agenda.domain.model

import java.time.LocalDateTime

data class Reminder(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: LocalDateTime,
    val time: LocalDateTime
)
