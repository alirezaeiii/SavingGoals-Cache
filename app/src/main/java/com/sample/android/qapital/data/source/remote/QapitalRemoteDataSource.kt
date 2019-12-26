package com.sample.android.qapital.data.source.remote

import com.sample.android.qapital.api.QapitalApi
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsRule
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QapitalRemoteDataSource @Inject constructor(
    private val api: QapitalApi
) {

    fun getFeeds(id: Int): Observable<List<Feed>> = api.requestFeeds(id).map { it.wrapper }

    fun getSavingsRules(): Observable<List<SavingsRule>> =
        api.requestSavingRules().map { it.wrapper }
}