package com.mk.tasky.agenda.data.local

import androidx.room.*
import com.mk.tasky.agenda.data.local.entity.*
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendee(attendee: AttendeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)
}
