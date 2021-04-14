package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.SavingsGoal
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {

    fun getSavingsGoals() : Observable<List<SavingsGoal>>

    fun insertAll(savingsGoals: List<SavingsGoal>) : Completable
}