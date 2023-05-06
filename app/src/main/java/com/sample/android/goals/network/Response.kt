package com.sample.android.goals.network

import com.google.gson.annotations.SerializedName
import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.data.SavingsGoalWrapper
import com.sample.android.goals.data.SavingsRule
import com.sample.android.goals.data.source.local.SavingsGoalEntity
import com.sample.android.goals.data.source.local.SavingsGoalWrapperEntity

class SavingsGoalWrapperResponse(
    @SerializedName("savingsGoals")
    val wrapper: List<SavingsGoalResponse>
)

class FeedWrapperResponse(
    @SerializedName("feed")
    val wrapper: List<FeedResponse>
)

class SavingsRuleWrapperResponse(
    @SerializedName("savingsRules")
    val wrapper: List<SavingsRuleResponse>
)

class SavingsGoalResponse(
    @SerializedName("goalImageURL")
    val imageUrl: String,
    val targetAmount: Float?,
    val currentBalance: Float,
    val name: String,
    val id: Int
)

class FeedResponse(
    val id: String,
    val type: String,
    val timestamp: String,
    val message: String,
    val amount: Float
)

class SavingsRuleResponse(
    val id: Int,
    val type: String
)

fun SavingsGoalWrapperResponse.asDatabaseModel(): SavingsGoalWrapperEntity {
    return SavingsGoalWrapperEntity(
        wrapper = this.wrapper.map {
            SavingsGoalEntity(
                imageUrl = it.imageUrl,
                targetAmount = it.targetAmount,
                currentBalance = it.currentBalance,
                name = it.name,
                id = it.id
            )
        }
    )
}

fun SavingsGoalWrapperResponse.asDomainModel(): SavingsGoalWrapper {
    return SavingsGoalWrapper(
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

fun List<FeedResponse>.asFeedDomainModel(): List<Feed> {
    return this.map {
        Feed(
            id = it.id, type = it.type, timestamp = it.timestamp,
            message = it.message, amount = it.amount
        )
    }
}

fun List<SavingsRuleResponse>.asSavingsRuleDomainModel(): List<SavingsRule> {
    return this.map { SavingsRule(id = it.id, type = it.type) }
}