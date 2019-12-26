package com.sample.android.qapital.ui.goals

import android.widget.ImageView
import com.sample.android.qapital.data.SavingsGoal

interface SavingsGoalClickCallback {
    fun onClick(savingsGoal: SavingsGoal, poster : ImageView)
}