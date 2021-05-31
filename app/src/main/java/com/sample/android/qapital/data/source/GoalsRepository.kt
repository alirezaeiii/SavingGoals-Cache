package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.network.SavingsGoalWrapper
import com.sample.android.qapital.network.asDatabaseModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
    private val remoteDataSource: QapitalService,
    private val localDataSource: LocalDataSource,
) : BaseRepository<SavingsGoalWrapper>() {

    override val resultRemoteDataSource: Observable<SavingsGoalWrapper>
        get() = remoteDataSource.requestSavingGoals().flatMap {
            localDataSource.insert(it.asDatabaseModel()).andThen(Observable.fromCallable { it })
        }.doOnComplete { cacheIsDirty = false }


    override val resultLocalDataSource: Observable<SavingsGoalWrapper>
        get() = localDataSource.getSavingsGoals()
}

