package com.sample.android.qapital.data.source.remote

import com.sample.android.qapital.data.SavingsGoal
import io.reactivex.Observable

interface RemoteDataSource {

    fun getSavingsGoals(): Observable<List<SavingsGoal>>
}