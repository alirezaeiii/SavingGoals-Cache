package com.sample.android.goals.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.data.SavingsGoalWrapper

@Entity(tableName = "goals")
class SavingsGoalWrapperEntity(
    @PrimaryKey val primaryKey: String = "primaryKey",
    val wrapper: List<SavingsGoalEntity>
)

class SavingsGoalEntity(
    val imageUrl: String,
    val targetAmount: Float?,
    val currentBalance: Float,
    val name: String,
    val id: Int
)

fun SavingsGoalWrapperEntity.asDomainModel(): SavingsGoalWrapper {
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