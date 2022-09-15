package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.TaskEntity
import com.mk.tasky.agenda.data.remote.dto.TaskDto
import com.mk.tasky.agenda.domain.model.Task
import com.mk.tasky.agenda.util.TimeUtil.longToTime
import com.mk.tasky.agenda.util.TimeUtil.timeToLong

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = timeToLong(this.remindAt),
        dateTime = timeToLong(this.dateTime),
        isDone = this.isDone
    )
}

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        time = timeToLong(this.dateTime),
        remindAt = timeToLong(this.remindAt),
        isDone = this.isDone
    )
}

fun TaskEntity.toDomain(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = longToTime(this.remindAt),
        dateTime = longToTime(this.dateTime),
        isDone = this.isDone
    )
}

fun TaskDto.toDomain(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = longToTime(this.remindAt),
        dateTime = longToTime(this.time),
        isDone = this.isDone
    )
}
