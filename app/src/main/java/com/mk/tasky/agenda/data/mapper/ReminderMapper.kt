package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.data.remote.dto.ReminderDto
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.toCurrentTime
import com.mk.tasky.agenda.util.toLong

fun AgendaItem.Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt.toLong(),
        dateTime = this.reminderDateTime.toLong()
    )
}

fun AgendaItem.Reminder.toDto(): ReminderDto {
    return ReminderDto(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.reminderDateTime.toLong(),
        remindAt = this.remindAt.toLong()
    )
}

fun ReminderEntity.toDomain(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        reminderId = this.id,
        reminderTitle = this.title,
        reminderDescription = this.description,
        reminderRemindAt = this.remindAt.toCurrentTime(),
        reminderDateTime = this.dateTime.toCurrentTime()
    )
}

fun ReminderDto.toDomain(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        reminderId = this.id,
        reminderTitle = this.title,
        reminderDescription = this.description,
        reminderRemindAt = this.remindAt.toCurrentTime(),
        reminderDateTime = this.time.toCurrentTime()
    )
}
