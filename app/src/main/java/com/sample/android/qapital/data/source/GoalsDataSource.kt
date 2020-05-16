package com.sample.android.qapital.data.source

import com.sample.android.qapital.data.SavingsGoal
import io.reactivex.Observable

interface GoalsDataSource {

    interface LoadGoalsCallback {

        fun onGoalsLoaded(savingsGoals: List<SavingsGoal>)

        fun onDataNotAvailable()
    }

    fun getSavingsGoals(callback: LoadGoalsCallback)

    fun getSavingsGoals(): Observable<List<SavingsGoal>>

    fun saveGoals(goals: Array<SavingsGoal>)

    fun refreshGoals()
}