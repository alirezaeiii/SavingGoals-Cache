package com.sample.android.qapital.data.source

import android.annotation.SuppressLint
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
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
        Observable.fromCallable { cacheIsDirty }
            .flatMap {
                if (it) {
                    getGoalsFromRemoteDataSource()
                } else {
                    localDataSource.getSavingsGoals()
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

    @SuppressLint("CheckResult")
    private fun refreshLocalDataSource(savingsGoals: Observable<List<SavingsGoal>>) {
        savingsGoals.subscribeOn(schedulerProvider.io())
            .doOnComplete { cacheIsDirty = false }
            .subscribe({ localDataSource.insertAll(it) }, {
                Timber.e(it)
            })
    }
}