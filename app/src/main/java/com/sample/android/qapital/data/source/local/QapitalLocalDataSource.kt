package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsDataSource
import com.sample.android.qapital.util.DiskIOThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
class QapitalLocalDataSource @Inject constructor(
    private val appExecutors: DiskIOThreadExecutor,
    private val goalsDao: GoalsDao
) : GoalsDataSource {

    override fun getSavingsGoals(callback: GoalsDataSource.LoadGoalsCallback) {
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

    override fun saveGoal(goal: SavingsGoal) {
        appExecutors.execute { goalsDao.insertGoal(goal) }
    }

    override fun deleteAllGoals() {
        appExecutors.execute { goalsDao.deleteGoals() }
    }

    override fun refreshGoals() {
        // Not required because the {@link GoalsRepository} handles the logic of refreshing the
        // goals from all the available data sources.
    }

    override fun getSavingsGoals(): Observable<List<SavingsGoal>> =
        Observable.just(goalsDao.getGoals())
}