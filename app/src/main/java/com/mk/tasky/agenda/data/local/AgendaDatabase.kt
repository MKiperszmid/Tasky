package com.mk.tasky.agenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.tasky.agenda.data.local.entity.*

@Database(
    entities = [ReminderEntity::class, TaskEntity::class, EventEntity::class, AttendeeEntity::class, PhotoEntity::class],
    version = 1
)
abstract class AgendaDatabase : RoomDatabase() {
    abstract val dao: AgendaDao
}
