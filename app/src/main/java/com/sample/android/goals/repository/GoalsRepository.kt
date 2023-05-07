package com.sample.android.goals.repository

import com.sample.android.goals.data.SavingsGoalWrapper
import com.sample.android.goals.data.source.BaseRepository
import com.sample.android.goals.data.source.local.LocalDataSource
import com.sample.android.goals.data.source.local.asDomainModel
import com.sample.android.goals.network.ApiService
import com.sample.android.goals.network.asDatabaseModel
import com.sample.android.goals.network.asDomainModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: LocalDataSource,
) : BaseRepository<SavingsGoalWrapper>() {

    override val resultRemoteDataSource: Observable<SavingsGoalWrapper>
        get() = remoteDataSource.requestSavingGoals().flatMap {
            localDataSource.insert(it.asDatabaseModel())
                .andThen(Observable.fromCallable { it.asDomainModel() })
        }.doOnComplete { cacheIsDirty = false }


    override val resultLocalDataSource: Observable<SavingsGoalWrapper>
        get() = localDataSource.getSavingsGoals().map { it.asDomainModel() }
}