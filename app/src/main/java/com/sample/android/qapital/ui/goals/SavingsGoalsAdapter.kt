package com.sample.android.qapital.ui.goals

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.qapital.R
import com.sample.android.qapital.databinding.SavingsGoalItemBinding
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.util.CurrencyFormatter
import com.sample.android.qapital.util.layoutInflater
import java.text.DecimalFormat
import java.text.NumberFormat


class SavingsGoalsAdapter(
    savingsGoals: List<SavingsGoal>,
    private val currencyFormatter: CurrencyFormatter,
    private val clickCallback: SavingsGoalClickCallback
) : RecyclerView.Adapter<SavingsGoalsAdapter.SavingsGoalViewHolder>() {

    private val numberFormat = NumberFormat.getCurrencyInstance()

    init {
        val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        numberFormat.decimalFormatSymbols = decimalFormatSymbols
        numberFormat.maximumFractionDigits = 0
    }

    private var savingsGoals: List<SavingsGoal> = savingsGoals
        set(savingsGoals) {
            field = savingsGoals
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: SavingsGoalViewHolder, position: Int) {
        with(holder.binding) {
            savingsGoal = savingsGoals[position]
            currentBalance = currencyFormatter.format(savingsGoals[position].currentBalance)
            targetAmount = savingsGoals[position].targetAmount?.let { numberFormat.format(it) }
            poster = imagePoster
            callback = clickCallback
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsGoalViewHolder {
        val binding: SavingsGoalItemBinding = DataBindingUtil
            .inflate(
                parent.context.layoutInflater,
                R.layout.savings_goal_item,
                parent, false
            )
        return SavingsGoalViewHolder(binding)
    }

    override fun getItemCount() = savingsGoals.size


    inner class SavingsGoalViewHolder(internal val binding: SavingsGoalItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}