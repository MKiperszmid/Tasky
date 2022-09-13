package com.mk.tasky.agenda.domain.usecase.task

import com.mk.tasky.agenda.domain.model.Task
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class SaveTask(
    private val repository: AgendaRepository
) {
    suspend operator fun invoke(
        id: String?,
        title: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        isDone: Boolean,
        reminder: NotificationTypes
    ) {
        val reminderTime = LocalDateTime.of(date, time)
        val remindAtTime = NotificationTypes.remindAt(reminderTime, reminder)
        val isEdit = id != null
        val agendaTask = Task(
            id = id ?: UUID.randomUUID().toString(),
            title = title,
            description = description,
            dateTime = reminderTime,
            remindAt = remindAtTime,
            isDone = isDone
        )

        repository.insertTask(agendaTask, isEdit)
    }
}
