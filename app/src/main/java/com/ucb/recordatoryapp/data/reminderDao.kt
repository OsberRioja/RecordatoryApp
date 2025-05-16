package com.ucb.recordatoryapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Query("SELECT * FROM reminder_table ORDER BY date ASC")
    fun getAll(): Flow<List<Reminder>>

    @Delete
    suspend fun delete(reminder: Reminder)
}