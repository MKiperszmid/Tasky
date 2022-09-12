package com.mk.tasky.agenda.domain.model

import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: LocalDateTime,
    val dateTime: LocalDateTime,
    val isDone: Boolean
)
