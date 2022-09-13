package com.mk.tasky.agenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.data.local.entity.TaskEntity

@Database(
    entities = [ReminderEntity::class, TaskEntity::class],
    version = 1
)
abstract class AgendaDatabase : RoomDatabase() {
    abstract val dao: AgendaDao
}
