package com.sample.android.qapital.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.databinding.SavingsGoalItemBinding
import com.sample.android.qapital.ui.MainAdapter.SavingsGoalViewHolder
import com.sample.android.qapital.util.formatter.CurrencyFormatter
import com.sample.android.qapital.util.formatter.NumberFormatter
import com.sample.android.qapital.util.layoutInflater

class MainAdapter(
    private val currencyFormatter: CurrencyFormatter,
    private val numberFormatter: NumberFormatter,
    private val callback: OnClickListener
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
        val binding = SavingsGoalItemBinding.inflate(
            parent.context.layoutInflater,
            parent, false
        )
        binding.callback = callback
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

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [SavingsGoal]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [SavingsGoal]
     */
    class OnClickListener(val clickListener: (savingsGoal: SavingsGoal) -> Unit) {
        fun onClick(savingsGoal: SavingsGoal) =
            clickListener(savingsGoal)
    }
}