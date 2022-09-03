package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.data.remote.dto.ReminderDto
import com.mk.tasky.agenda.domain.model.Reminder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = timeToLong(this.remindAt),
        dateTime = timeToLong(this.dateTime)
    )
}

fun Reminder.toDto(): ReminderDto {
    return ReminderDto(
        id = this.id,
        title = this.title,
        description = this.description,
        time = timeToLong(this.dateTime),
        remindAt = timeToLong(this.remindAt)
    )
}

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = longToTime(this.remindAt),
        dateTime = longToTime(this.dateTime)
    )
}

private fun timeToLong(time: LocalDateTime): Long {
    return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

private fun longToTime(time: Long): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(time),
        ZoneId.systemDefault()
    )
}
