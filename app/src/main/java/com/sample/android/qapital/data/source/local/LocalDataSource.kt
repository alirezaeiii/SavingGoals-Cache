package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
import io.reactivex.Single

interface LocalDataSource {

    fun getSavingsGoals() : Single<List<SavingsGoal>>

    fun insertAll(savingsGoals: List<SavingsGoal>)
}