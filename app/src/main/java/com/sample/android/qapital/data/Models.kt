package com.sample.android.qapital.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "goals")
data class SavingsGoal(
    @SerializedName("goalImageURL")
    val imageUrl: String,
    val targetAmount: Float?,
    val currentBalance: Float,
    val name: String,
    @PrimaryKey @ColumnInfo(name = "entryid")
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