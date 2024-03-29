package com.sample.android.goals.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

/**
 * Data Access Object for the goals table.
 */
@Dao
interface GoalsDao {

    /**
     * Select all goals from the goals table.
     *
     * @return all goals.
     */
    @Query("SELECT * FROM Goals") fun getGoals(): SavingsGoalWrapperEntity?

    /**
     * Insert a goal in the database. If the goal already exists, replace it.
     *
     * @param goals the goals to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(goals: SavingsGoalWrapperEntity) : Completable
}