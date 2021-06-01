package com.sample.android.qapital.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sample.android.qapital.network.SavingsGoalWrapper
import com.sample.android.qapital.util.CurrencyFormatterDefault
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

@Entity(tableName = "goals")
class DbSavingsGoalWrapper(
    @PrimaryKey val primaryKey: String = "primaryKey",
    @SerializedName("savingsGoals")
    val wrapper: List<SavingsGoal>
)

@Parcelize
data class SavingsGoal(
    @SerializedName("goalImageURL")
    val imageUrl: String,
    val targetAmount: Float?,
    val currentBalance: Float,
    val name: String,
    val id: Int
) : Parcelable

class Feed(
    val id: String,
    val type: String,
    val timestamp: String,
    val message: String,
    val amount: Float
)

class SavingsRule(
    val id: Int,
    val type: String
)

fun DbSavingsGoalWrapper.asDomainModel(): SavingsGoalWrapper {
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

fun List<Feed>.asWeekSumText(currencyFormatter: CurrencyFormatterDefault): String {
    var weekSum = 0f
    for (feed in this) {
        weekSum += getAmountIfInCurrentWeek(feed)
    }
    return currencyFormatter.format(weekSum)
}

private fun getAmountIfInCurrentWeek(feed: Feed): Float {
    val timestamp = ZonedDateTime.parse(feed.timestamp)
    val now = ZonedDateTime.now()
    return if (now.minus(1, ChronoUnit.WEEKS).isBefore(timestamp)) {
        feed.amount
    } else 0f
}