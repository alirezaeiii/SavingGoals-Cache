package com.sample.android.qapital.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.android.qapital.data.DbSavingsGoalWrapper

/**
 * The Room Database that contains the Goal table.
 */
@Database(entities = [DbSavingsGoalWrapper::class], version = 1, exportSchema = false)
@TypeConverters(GoalsConverter::class)
abstract class QapitalDatabase : RoomDatabase() {

    abstract fun goalDao(): GoalsDao
}