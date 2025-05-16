package com.ucb.recordatoryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminder_table")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Date,
    val importance: Importance
)

enum class Importance { NORMAL, IMPORTANTE, MUY_IMPORTANTE }