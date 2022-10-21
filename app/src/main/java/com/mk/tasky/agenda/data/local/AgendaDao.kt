package com.mk.tasky.agenda.data.local

import androidx.room.*
import com.mk.tasky.agenda.data.local.entity.*
import com.mk.tasky.agenda.data.local.entity.relations.EventAttendeesCrossRef
import com.mk.tasky.agenda.data.local.entity.relations.EventWithAttendees

@Dao
interface AgendaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE id = :id
        """
    )
    suspend fun getReminderById(id: String): ReminderEntity

    @Query("DELETE FROM ReminderEntity WHERE id = :id")
    suspend fun deleteReminderById(id: String)

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE dateTime >= :dayOne
            AND dateTime < :dayTwo
        """
    )
    suspend fun getRemindersBetweenTimestamps(dayOne: Long, dayTwo: Long): List<ReminderEntity>

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE dateTime >= :startingDate
        """
    )
    suspend fun getAllFutureReminders(startingDate: Long): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query(
        """
            SELECT * FROM TaskEntity
            WHERE id = :id
        """
    )
    suspend fun getTaskById(id: String): TaskEntity

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query(
        """
            SELECT * FROM TaskEntity
            WHERE dateTime >= :dayOne
            AND dateTime < :dayTwo
        """
    )
    suspend fun getTasksBetweenTimestamps(dayOne: Long, dayTwo: Long): List<TaskEntity>

    @Query(
        """
            SELECT * FROM TaskEntity
            WHERE dateTime >= :startingDate
        """
    )
    suspend fun getAllFutureTasks(startingDate: Long): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendee(attendee: AttendeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventAttendeeCrossRef(ref: EventAttendeesCrossRef)

    @Query(
        """
            SELECT * FROM EventEntity
            WHERE eventId = :id
        """
    )
    suspend fun getEventById(id: String): EventWithAttendees

    @Query(
        """
            SELECT * FROM EventEntity
            WHERE fromDateTime >= :dayOne
            AND fromDateTime < :dayTwo
        """
    )
    @Transaction
    suspend fun getEventsBetweenTimestamps(dayOne: Long, dayTwo: Long): List<EventWithAttendees>

    @Query(
        """
            SELECT * FROM EventEntity
            WHERE fromDateTime >= :startingDate
        """
    )
    suspend fun getAllFutureEvents(startingDate: Long): List<EventWithAttendees>

    @Query("DELETE FROM EventEntity WHERE eventId = :id")
    suspend fun deleteEventById(id: String)

    @Query("DELETE FROM EventAttendeesCrossRef WHERE eventId = :id")
    suspend fun deleteEventCrossRefById(id: String)

    @Query("SELECT * FROM SyncEntity")
    suspend fun getAllSyncItems(): List<SyncEntity>

    @Query("DELETE FROM SyncEntity WHERE id = :id")
    suspend fun deleteSyncItem(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncItem(item: SyncEntity)
}
