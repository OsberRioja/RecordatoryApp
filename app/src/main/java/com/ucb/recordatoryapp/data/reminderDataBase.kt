package com.ucb.recordatoryapp.data

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.util.Date

@Database(entities = [Reminder::class], version = 1)
@TypeConverters(DateConverter::class, ImportanceConverter::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile private var INSTANCE: ReminderDatabase? = null
        fun getDatabase(context: Context): ReminderDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDatabase::class.java,
                    "reminder_db"
                ).build().also { INSTANCE = it }
            }
    }
}

// Converters
class DateConverter {
    @TypeConverter fun toDate(ts: Long?) = ts?.let { Date(it) }
    @TypeConverter fun toTimestamp(date: Date?) = date?.time
}

class ImportanceConverter {
    @TypeConverter fun toImp(value: String) = Importance.valueOf(value)
    @TypeConverter fun fromImp(imp: Importance) = imp.name
}