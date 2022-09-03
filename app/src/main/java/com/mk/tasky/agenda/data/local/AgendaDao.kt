package com.mk.tasky.agenda.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mk.tasky.agenda.data.local.entity.ReminderEntity

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
}
