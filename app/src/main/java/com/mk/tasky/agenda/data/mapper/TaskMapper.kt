package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.TaskEntity
import com.mk.tasky.agenda.data.remote.dto.TaskDto
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.TimeUtil.longToTime
import com.mk.tasky.agenda.util.TimeUtil.timeToLong

fun AgendaItem.Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.taskId,
        title = this.taskTitle,
        description = this.taskDescription,
        remindAt = timeToLong(this.taskRemindAt),
        dateTime = timeToLong(this.taskDateTime),
        isDone = this.isDone
    )
}

fun AgendaItem.Task.toDto(): TaskDto {
    return TaskDto(
        id = this.taskId,
        title = this.taskTitle,
        description = this.taskDescription,
        time = timeToLong(this.taskDateTime),
        remindAt = timeToLong(this.taskRemindAt),
        isDone = this.isDone
    )
}

fun TaskEntity.toDomain(): AgendaItem.Task {
    return AgendaItem.Task(
        taskId = this.id,
        taskTitle = this.title,
        taskDescription = this.description,
        taskRemindAt = longToTime(this.remindAt),
        taskDateTime = longToTime(this.dateTime),
        isDone = this.isDone
    )
}

fun TaskDto.toDomain(): AgendaItem.Task {
    return AgendaItem.Task(
        taskId = this.id,
        taskTitle = this.title,
        taskDescription = this.description,
        taskRemindAt = longToTime(this.remindAt),
        taskDateTime = longToTime(this.time),
        isDone = this.isDone
    )
}
