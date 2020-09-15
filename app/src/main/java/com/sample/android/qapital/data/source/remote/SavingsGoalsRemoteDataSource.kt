package com.sample.android.qapital.data.source.remote

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.network.QapitalService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavingsGoalsRemoteDataSource @Inject constructor(
    private val api: QapitalService
) : RemoteDataSource {

    override fun getSavingsGoals(): Observable<List<SavingsGoal>> =
        api.requestSavingGoals().map { it.wrapper }
}