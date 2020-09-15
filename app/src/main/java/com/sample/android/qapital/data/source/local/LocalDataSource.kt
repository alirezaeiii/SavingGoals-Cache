package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal

interface LocalDataSource {

    interface LoadGoalsCallback {

        fun onGoalsLoaded(savingsGoals: List<SavingsGoal>)

        fun onDataNotAvailable()
    }

    fun getSavingsGoals(callback: LoadGoalsCallback)
}