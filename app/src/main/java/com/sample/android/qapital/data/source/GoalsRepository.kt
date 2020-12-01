package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
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
        Observable.fromCallable { cacheIsDirty }
            .flatMap {
                if (it) {
                    getGoalsFromRemoteDataSource()
                } else {
                    localDataSource.getSavingsGoals()
                        .observeOn(schedulerProvider.io())
                        .toObservable()
                        .onErrorResumeNext(getGoalsFromRemoteDataSource())
                }
            }

    fun refreshGoals() {
        cacheIsDirty = true
    }

    private fun getGoalsFromRemoteDataSource(): Observable<List<SavingsGoal>> {
        val savingsGoals = remoteDataSource.requestSavingGoals().map { it.wrapper }
        refreshLocalDataSource(savingsGoals)
        return savingsGoals
    }

    private fun refreshLocalDataSource(savingsGoals: Observable<List<SavingsGoal>>) {
        var disposable: Disposable? = null
        disposable = savingsGoals.subscribeOn(schedulerProvider.io())
            .doOnComplete { cacheIsDirty = false }
            .doFinally { disposable?.dispose() }
            .subscribe { localDataSource.insertAll(it) }
    }
}