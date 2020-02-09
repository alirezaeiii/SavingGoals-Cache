package com.sample.android.qapital.usecase

import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsRepository
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavingsGoalsUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: GoalsRepository
) : BaseUseCase(schedulerProvider) {

    fun getSavingsGoals(): Observable<List<SavingsGoal>> =
        composeObservable { repository.getSavingsGoals() }
}