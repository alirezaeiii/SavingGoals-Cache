package com.sample.android.qapital.api

import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.squareup.moshi.Json
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface QapitalApi {

    @GET("/savingsgoals")
    fun requestSavingGoals(): Observable<SavingsGoalWrapper>

    @GET("/savingsgoals/{id}/feed")
    fun requestFeeds(@Path("id") id: Int) : Observable<FeedWrapper>

    @GET("/savingsrules")
    fun requestSavingRules() : Observable<SavingsRuleWrapper>

    class SavingsGoalWrapper(
        @field:Json(name = "savingsGoals")
        val wrapper: List<SavingsGoal>
    )

    class FeedWrapper(
        @field:Json(name = "feed")
        val wrapper : List<Feed>
    )

    class SavingsRuleWrapper(
        @field:Json(name = "savingsRules")
        val wrapper : List<SavingsRule>
    )
}