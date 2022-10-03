package com.mk.tasky.agenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.tasky.agenda.data.local.entity.AttendeeEntity
import com.mk.tasky.agenda.data.local.entity.EventEntity
import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.data.local.entity.TaskEntity
import com.mk.tasky.agenda.data.local.entity.relations.EventAttendeesCrossRef

@Database(
    entities = [ReminderEntity::class, TaskEntity::class, EventEntity::class, AttendeeEntity::class, EventAttendeesCrossRef::class],
    version = 1
)
abstract class AgendaDatabase : RoomDatabase() {
    abstract val dao: AgendaDao
}
