package com.sample.android.goals.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
/**
 * The Room Database that contains the Goal table.
 */
@Database(entities = [SavingsGoalWrapperEntity::class], version = 1, exportSchema = false)
@TypeConverters(GoalsConverter::class)
abstract class GoalsDatabase : RoomDatabase() {

    abstract fun goalDao(): GoalsDao
}