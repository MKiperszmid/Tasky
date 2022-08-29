package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.domain.model.Reminder
import java.util.*

fun Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id ?: UUID.randomUUID().toString(),
        title = this.title,
        description = this.description,
        remindAt = this.remindAt.toString(),
        time = this.time.toString()
    )
}
