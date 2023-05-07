package com.sample.android.goals.data.local

import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {

    fun getSavingsGoals() : Observable<SavingsGoalWrapperEntity>

    fun insert(savingsGoals: SavingsGoalWrapperEntity) : Completable
}