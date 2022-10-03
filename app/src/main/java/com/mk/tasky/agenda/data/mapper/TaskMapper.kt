package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.local.entity.TaskEntity
import com.mk.tasky.agenda.data.remote.dto.TaskDto
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.toCurrentTime
import com.mk.tasky.agenda.util.toLong

fun AgendaItem.Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.taskId,
        title = this.taskTitle,
        description = this.taskDescription,
        remindAt = this.taskRemindAt.toLong(),
        dateTime = this.taskDateTime.toLong(),
        isDone = this.isDone
    )
}

fun AgendaItem.Task.toDto(): TaskDto {
    return TaskDto(
        id = this.taskId,
        title = this.taskTitle,
        description = this.taskDescription,
        time = this.taskDateTime.toLong(),
        remindAt = this.taskRemindAt.toLong(),
        isDone = this.isDone
    )
}

fun TaskEntity.toDomain(): AgendaItem.Task {
    return AgendaItem.Task(
        taskId = this.id,
        taskTitle = this.title,
        taskDescription = this.description,
        taskRemindAt = this.remindAt.toCurrentTime(),
        taskDateTime = this.dateTime.toCurrentTime(),
        isDone = this.isDone
    )
}

fun TaskDto.toDomain(): AgendaItem.Task {
    return AgendaItem.Task(
        taskId = this.id,
        taskTitle = this.title,
        taskDescription = this.description,
        taskRemindAt = this.remindAt.toCurrentTime(),
        taskDateTime = this.time.toCurrentTime(),
        isDone = this.isDone
    )
}
