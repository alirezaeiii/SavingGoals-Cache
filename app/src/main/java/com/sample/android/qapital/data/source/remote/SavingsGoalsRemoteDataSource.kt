package com.sample.android.qapital.data.source.remote

import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsDataSource
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavingsGoalsRemoteDataSource @Inject constructor(
    private val api: QapitalService
) : GoalsDataSource {

    override fun getSavingsGoals(): Observable<List<SavingsGoal>> =
        api.requestSavingGoals().map { it.wrapper }


    override fun saveGoal(goal: SavingsGoal) {}

    override fun deleteAllGoals() {}

    override fun refreshGoals() {
        // Not required because the {@link GoalsRepository} handles the logic of refreshing the
        // goals from all the available data sources.
    }

    override fun getSavingsGoals(callback: GoalsDataSource.LoadGoalsCallback) {}
}