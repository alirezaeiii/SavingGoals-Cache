package com.sample.android.qapital.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.android.qapital.data.SavingsGoal
import io.reactivex.Observable

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
    @Query("SELECT * FROM Goals") fun getGoals(): List<SavingsGoal>

    /**
     * Insert a goal in the database. If the goal already exists, replace it.
     *
     * @param goal the goal to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertAll(vararg goals: SavingsGoal)
}