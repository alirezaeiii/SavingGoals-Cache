package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
        private val remoteDataSource: QapitalService,
        private val localDataSource: LocalDataSource
) {

    private var cacheIsDirty = false

    fun getSavingsGoals(): Observable<List<SavingsGoal>> =
            Observable.fromCallable { cacheIsDirty }
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

    private fun getGoalsFromRemoteDataSource(): Observable<List<SavingsGoal>> =
        remoteDataSource.requestSavingGoals().map { it.wrapper }.flatMap {
                    localDataSource.insertAll(it).andThen(Observable.fromCallable { it })
                }.doOnComplete { cacheIsDirty = false }
}
