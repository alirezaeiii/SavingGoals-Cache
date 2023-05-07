package com.sample.android.goals.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sample.android.goals.data.SavingsGoal

object GoalsConverter {

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<SavingsGoalEntity> =
        Gson().fromJson(value, Array<SavingsGoalEntity>::class.java).toList()

    @TypeConverter
    @JvmStatic
    fun listToJson(list: List<SavingsGoalEntity>): String = Gson().toJson(list)
}