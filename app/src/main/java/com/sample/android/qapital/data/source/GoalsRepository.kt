package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.usecase.BaseUseCase
import com.sample.android.qapital.util.DiskIOThreadExecutor
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
    @param:Remote private val remoteDataSource: GoalsDataSource,
    @param:Local private val localDataSource: GoalsDataSource,
    private val appExecutors: DiskIOThreadExecutor,
    schedulerProvider: BaseSchedulerProvider
) : GoalsDataSource, BaseUseCase(schedulerProvider) {

    private var cacheIsDirty = false

    override fun getSavingsGoals(): Observable<List<SavingsGoal>> {

        lateinit var items: Observable<List<SavingsGoal>>
        if (cacheIsDirty) {
            items = getGoalsFromRemoteDataSource()
        } else {
            val countDownLatch = CountDownLatch(1)
            localDataSource.getSavingsGoals(object : GoalsDataSource.LoadGoalsCallback {
                override fun onGoalsLoaded(savingsGoals: List<SavingsGoal>) {
                    items = Observable.just(savingsGoals)
                    countDownLatch.countDown()
                }

                override fun onDataNotAvailable() {
                    items = getGoalsFromRemoteDataSource()
                    countDownLatch.countDown()
                }
            })
            countDownLatch.await()
        }
        return composeObservable { items }
    }

    override fun saveGoal(goal: SavingsGoal) {
        remoteDataSource.saveGoal(goal)
        localDataSource.saveGoal(goal)
    }

    override fun deleteAllGoals() {
        remoteDataSource.deleteAllGoals()
        localDataSource.deleteAllGoals()
    }

    override fun refreshGoals() {
        cacheIsDirty = true
    }


    override fun getSavingsGoals(callback: GoalsDataSource.LoadGoalsCallback) {}

    private fun getGoalsFromRemoteDataSource(): Observable<List<SavingsGoal>> {
        val goals = remoteDataSource.getSavingsGoals()
        refreshLocalDataSource(goals)
        return goals
    }

    private fun refreshLocalDataSource(goals: Observable<List<SavingsGoal>>) {
        localDataSource.deleteAllGoals()
        appExecutors.execute {
            goals.doOnComplete {
                cacheIsDirty = false
            }.subscribe({ savingsGoals ->
                for (goal in savingsGoals) {
                    localDataSource.saveGoal(goal)
                }
            }) { Timber.e(it) }
        }
    }
}