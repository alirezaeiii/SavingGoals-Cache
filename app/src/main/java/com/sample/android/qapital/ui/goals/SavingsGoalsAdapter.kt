package com.sample.android.qapital.ui.goals

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.qapital.R
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.ui.goals.SavingsGoalsAdapter.SavingsGoalViewHolder
import com.sample.android.qapital.databinding.SavingsGoalItemBinding
import com.sample.android.qapital.util.CurrencyFormatter
import com.sample.android.qapital.util.NumberFormatter
import com.sample.android.qapital.util.layoutInflater


class SavingsGoalsAdapter(
    private val currencyFormatter: CurrencyFormatter,
    private val numberFormatter: NumberFormatter,
    private val clickCallback: SavingsGoalClickCallback
) : ListAdapter<SavingsGoal, SavingsGoalViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: SavingsGoalViewHolder, position: Int) {
        val savingsGoalItem = getItem(position)
        with(holder.binding) {
            savingsGoal = savingsGoalItem
            currentBalance = currencyFormatter.format(savingsGoalItem.currentBalance)
            targetAmount = savingsGoalItem.targetAmount?.let { numberFormatter.format(it) }
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
        with(binding) {
            poster = imagePoster
            callback = clickCallback
        }
        return SavingsGoalViewHolder(binding)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [SavingsGoal]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<SavingsGoal>() {
        override fun areItemsTheSame(
            oldItem: SavingsGoal, newItem: SavingsGoal
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SavingsGoal, newItem: SavingsGoal
        ): Boolean {
            return oldItem == newItem
        }
    }

    class SavingsGoalViewHolder(val binding: SavingsGoalItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}