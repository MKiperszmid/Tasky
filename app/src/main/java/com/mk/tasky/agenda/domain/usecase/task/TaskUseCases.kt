package com.mk.tasky.agenda.domain.usecase.task

data class TaskUseCases(
    val getTask: GetTask,
    val saveTask: SaveTask,
    val deleteTask: DeleteTask,
    val changeStatusTask: ChangeStatusTask
)
