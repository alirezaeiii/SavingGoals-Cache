package com.sample.android.goals.data.source.local

import com.sample.android.goals.network.SavingsGoalWrapper
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {

    fun getSavingsGoals() : Observable<SavingsGoalWrapper>

    fun insert(savingsGoals: SavingsGoalWrapper) : Completable
}