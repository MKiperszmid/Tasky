package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.domain.model.Reminder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id ?: UUID.randomUUID().toString(),
        title = this.title,
        description = this.description,
        remindAt = this.remindAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        day = this.time.dayOfMonth,
        month = this.time.monthValue,
        year = this.time.year,
        hour = this.time.hour,
        minute = this.time.minute
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this.remindAt),
            ZoneId.systemDefault()
        ),
        time = LocalDateTime.of(year, month, day, hour, minute)
    )
}
