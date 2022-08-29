package com.mk.tasky.agenda.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mk.tasky.agenda.data.local.entity.ReminderEntity
import com.mk.tasky.agenda.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE day = :day
            AND month = :month
            AND year = :year
        """
    )
    fun getRemindersForDate(day: Int, month: Int, year: Int): Flow<List<ReminderEntity>>
}
