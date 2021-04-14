package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
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

    override fun getSavingsGoals(): Observable<List<SavingsGoal>> =
            Observable.create { subscriber ->
                goalsDao.getGoals().subscribe {
                    if (it.isEmpty()) {
                        subscriber.onError(NoDataException())
                    } else {
                        subscriber.onNext(it)
                    }
                }
            }

    override fun insertAll(savingsGoals: List<SavingsGoal>): Completable =
            goalsDao.insertAll(*savingsGoals.toTypedArray())
}