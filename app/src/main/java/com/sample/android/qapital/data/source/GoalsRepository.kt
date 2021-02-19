package com.sample.android.qapital.data.source

import android.annotation.SuppressLint
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
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

    fun getSavingsGoals(): Single<List<SavingsGoal>> =
        Single.fromCallable { cacheIsDirty }
            .flatMap {
                if (it) {
                    getGoalsFromRemoteDataSource()
                } else {
                    localDataSource.getSavingsGoals()
                        .onErrorResumeNext(getGoalsFromRemoteDataSource())
                }
            }

    fun refreshGoals() {
        cacheIsDirty = true
    }

    private fun getGoalsFromRemoteDataSource(): Single<List<SavingsGoal>> {
        val savingsGoals = remoteDataSource.requestSavingGoals().map { it.wrapper }
        refreshLocalDataSource(savingsGoals.toObservable())
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