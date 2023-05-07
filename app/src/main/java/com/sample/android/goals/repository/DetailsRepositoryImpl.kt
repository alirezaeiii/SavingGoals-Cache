package com.sample.android.goals.repository

import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsRule
import com.sample.android.goals.data.source.DetailsRepository
import com.sample.android.goals.network.ApiService
import com.sample.android.goals.network.asFeedDomainModel
import com.sample.android.goals.network.asSavingsRuleDomainModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService
) : DetailsRepository {

    override fun getFeeds(id: Int): Observable<List<Feed>> =
        remoteDataSource.requestFeeds(id).map { it.wrapper }.map { it.asFeedDomainModel() }

    override fun getSavingRules(): Observable<List<SavingsRule>> =
        remoteDataSource.requestSavingRules().map { it.wrapper }
            .map { it.asSavingsRuleDomainModel() }
}