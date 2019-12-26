package com.sample.android.qapital.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "goals")
class SavingsGoal(
    @field:Json(name = "goalImageURL") val imageUrl: String,
    val targetAmount: Float?,
    val currentBalance: Float,
    val name: String,
    @PrimaryKey @ColumnInfo(name = "entryid") val id: Int
) : Parcelable