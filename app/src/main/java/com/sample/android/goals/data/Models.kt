package com.sample.android.goals.data

import android.os.Parcelable
import com.sample.android.goals.util.formatter.DefaultCurrencyFormatter
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

class SavingsGoalWrapper(
    val wrapper: List<SavingsGoal>
)

@Parcelize
data class SavingsGoal(
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

fun List<Feed>.asWeekSumText(currencyFormatter: DefaultCurrencyFormatter): String {
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