package com.sample.android.goals.data

import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsRule
import io.reactivex.Observable

interface DetailsRepository {

    fun getFeeds(id: Int): Observable<List<Feed>>

    fun getSavingRules(): Observable<List<SavingsRule>>
}