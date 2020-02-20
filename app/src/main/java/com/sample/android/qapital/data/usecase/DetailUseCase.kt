package com.sample.android.qapital.data.usecase

import com.sample.android.qapital.api.QapitalApi
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsRule
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val api: QapitalApi
) : BaseUseCase(schedulerProvider) {

    fun getFeeds(id: Int): Observable<List<Feed>> =
        composeObservable { api.requestFeeds(id).map { it.wrapper } }

    fun getSavingsRules(): Observable<List<SavingsRule>> =
        composeObservable { api.requestSavingRules().map { it.wrapper } }
}