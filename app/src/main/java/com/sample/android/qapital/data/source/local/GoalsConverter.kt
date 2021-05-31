package com.sample.android.qapital.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sample.android.qapital.data.SavingsGoal

object GoalsConverter {

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<SavingsGoal> =
        Gson().fromJson(value, Array<SavingsGoal>::class.java).toList()

    @TypeConverter
    @JvmStatic
    fun listToJson(list: List<SavingsGoal>): String = Gson().toJson(list)
}