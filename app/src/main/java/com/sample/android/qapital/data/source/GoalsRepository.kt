package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.DiskIOThreadExecutor
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
    private val remoteDataSource: QapitalService,
    private val localDataSource: LocalDataSource,
    private val appExecutors: DiskIOThreadExecutor
) {

    private var cacheIsDirty = false

    fun getSavingsGoals(): Observable<List<SavingsGoal>> {

        lateinit var items: Observable<List<SavingsGoal>>
        if (cacheIsDirty) {
            items = getGoalsFromRemoteDataSource()
        } else {
            val countDownLatch = CountDownLatch(1)
            localDataSource.getSavingsGoals(object : LocalDataSource.LoadGoalsCallback {
                override fun onGoalsLoaded(savingsGoals: List<SavingsGoal>) {
                    items = Observable.create { emitter -> emitter.onNext(savingsGoals) }
                    countDownLatch.countDown()
                }

                override fun onDataNotAvailable() {
                    items = getGoalsFromRemoteDataSource()
                    countDownLatch.countDown()
                }
            })
            countDownLatch.await()
        }
        return items
    }

    fun refreshGoals() {
        cacheIsDirty = true
    }

    private fun getGoalsFromRemoteDataSource(): Observable<List<SavingsGoal>> {
        val goals = remoteDataSource.requestSavingGoals().map { it.wrapper }
        refreshLocalDataSource(goals)
        return goals
    }

    private fun refreshLocalDataSource(goals: Observable<List<SavingsGoal>>) {
        appExecutors.execute {
            goals.doOnComplete {
                cacheIsDirty = false
            }.subscribe({ savingsGoals ->
                localDataSource.saveGoals(savingsGoals.toTypedArray())
            }) { Timber.e(it) }
        }
    }
}