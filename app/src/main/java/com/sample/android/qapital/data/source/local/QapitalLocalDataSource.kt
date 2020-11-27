package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.util.DiskIOThreadExecutor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
class QapitalLocalDataSource @Inject constructor(
    private val appExecutors: DiskIOThreadExecutor,
    private val goalsDao: GoalsDao
) : LocalDataSource {

    override fun getSavingsGoals(callback: LocalDataSource.LoadGoalsCallback) {
        appExecutors.execute {
            val goals = goalsDao.getGoals()
            if (goals.isEmpty()) {
                // This will be called if the table is new or just empty.
                callback.onDataNotAvailable()
            } else {
                callback.onGoalsLoaded(goals)
            }
        }
    }

    override fun insertAll(savingsGoals: List<SavingsGoal>) {
        goalsDao.insertAll(*savingsGoals.toTypedArray())
    }
}