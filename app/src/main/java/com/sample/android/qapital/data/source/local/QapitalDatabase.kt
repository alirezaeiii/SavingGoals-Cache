package com.sample.android.qapital.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.android.qapital.data.SavingsGoal

/**
 * The Room Database that contains the Goal table.
 */
@Database(entities = [SavingsGoal::class], version = 1, exportSchema = false)
abstract class QapitalDatabase : RoomDatabase() {

    abstract fun goalDao(): GoalsDao
}