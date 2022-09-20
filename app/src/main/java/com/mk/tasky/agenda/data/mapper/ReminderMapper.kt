package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.data.remote.dto.ReminderDto
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.TimeUtil.longToTime
import com.mk.tasky.agenda.util.TimeUtil.timeToLong

fun AgendaItem.Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = timeToLong(this.remindAt),
        dateTime = timeToLong(this.reminderDateTime)
    )
}

fun AgendaItem.Reminder.toDto(): ReminderDto {
    return ReminderDto(
        id = this.id,
        title = this.title,
        description = this.description,
        time = timeToLong(this.reminderDateTime),
        remindAt = timeToLong(this.remindAt)
    )
}

fun ReminderEntity.toDomain(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        reminderId = this.id,
        reminderTitle = this.title,
        reminderDescription = this.description,
        reminderRemindAt = longToTime(this.remindAt),
        reminderDateTime = longToTime(this.dateTime)
    )
}

fun ReminderDto.toDomain(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        reminderId = this.id,
        reminderTitle = this.title,
        reminderDescription = this.description,
        reminderRemindAt = longToTime(this.remindAt),
        reminderDateTime = longToTime(this.time)
    )
}
