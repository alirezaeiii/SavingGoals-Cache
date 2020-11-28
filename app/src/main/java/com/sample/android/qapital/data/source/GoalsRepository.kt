package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
    private val remoteDataSource: QapitalService,
    private val localDataSource: LocalDataSource,
    private val schedulerProvider: BaseSchedulerProvider
) {

    private var cacheIsDirty = false

    fun getSavingsGoals(): Observable<List<SavingsGoal>> =
        if (cacheIsDirty) {
            getGoalsFromRemoteDataSource()
        } else {
            localDataSource.getSavingsGoals().toObservable()
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
        var disposable: Disposable? = null
        disposable = goals.subscribeOn(schedulerProvider.io())
            .doOnComplete { cacheIsDirty = false }
            .doFinally { disposable?.dispose() }
            .subscribe({ savingsGoals ->
                localDataSource.insertAll(savingsGoals)
            }) { Timber.e(it) }
    }
}