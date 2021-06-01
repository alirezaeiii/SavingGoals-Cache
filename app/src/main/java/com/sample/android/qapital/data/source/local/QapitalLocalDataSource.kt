package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.asDomainModel
import com.sample.android.qapital.network.SavingsGoalWrapper
import com.sample.android.qapital.network.asDatabaseModel
import com.sample.android.qapital.util.NoDataException
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
class QapitalLocalDataSource @Inject constructor(
    private val goalsDao: GoalsDao
) : LocalDataSource {

    override fun getSavingsGoals(): Observable<SavingsGoalWrapper> =
        Observable.create { subscriber ->
            val goals = goalsDao.getGoals()?.asDomainModel()
            if (goals == null) {
                subscriber.onError(NoDataException())
            } else {
                subscriber.onNext(goals)
            }
        }

    override fun insert(savingsGoals: SavingsGoalWrapper): Completable =
        goalsDao.insert(savingsGoals.asDatabaseModel())
}