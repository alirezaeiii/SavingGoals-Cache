package com.sample.android.qapital.data.source.local

import com.sample.android.qapital.data.DbSavingsGoalWrapper
import com.sample.android.qapital.network.SavingsGoalWrapper
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {

    fun getSavingsGoals() : Observable<SavingsGoalWrapper>

    fun insert(savingsGoals: DbSavingsGoalWrapper) : Completable
}