package com.sample.android.goals.data.local

import com.sample.android.goals.util.NoDataException
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val goalsDao: GoalsDao
) : LocalDataSource {

    override fun getSavingsGoals(): Observable<SavingsGoalWrapperEntity> =
        Observable.create { subscriber ->
            val goals = goalsDao.getGoals()
            if (goals == null) {
                subscriber.onError(NoDataException())
            } else {
                subscriber.onNext(goals)
            }
        }

    override fun insert(savingsGoals: SavingsGoalWrapperEntity): Completable =
        goalsDao.insert(savingsGoals)
}