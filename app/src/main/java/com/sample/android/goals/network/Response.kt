package com.sample.android.goals.network

import com.google.gson.annotations.SerializedName
import com.sample.android.goals.data.DbSavingsGoalWrapper
import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.data.SavingsRule

class SavingsGoalWrapper(
    @SerializedName("savingsGoals")
    val wrapper: List<SavingsGoal>
)

class FeedWrapper(
    @SerializedName("feed")
    val wrapper: List<Feed>
)

class SavingsRuleWrapper(
    @SerializedName("savingsRules")
    val wrapper: List<SavingsRule>
)

fun SavingsGoalWrapper.asDatabaseModel(): DbSavingsGoalWrapper {
    return DbSavingsGoalWrapper(
        wrapper = this.wrapper.map {
            SavingsGoal(
                imageUrl = it.imageUrl,
                targetAmount = it.targetAmount,
                currentBalance = it.currentBalance,
                name = it.name,
                id = it.id
            )
        }
    )
}