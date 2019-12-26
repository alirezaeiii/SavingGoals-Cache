package com.sample.android.qapital.ui.detail

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.qapital.R
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.databinding.FeedItemBinding
import com.sample.android.qapital.util.*
import java.util.*

class FeedAdapter(
    feeds: List<Feed>
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val currencyFormatterFraction = CurrencyFormatterFraction(Locale.getDefault())
    private val dateTimeParser = DateTimeParser

    private var feeds: List<Feed> = feeds
        set(feeds) {
            field = feeds
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        with(holder.binding) {
            message.text = feeds[position].message.fromHtml()
            amount.text = currencyFormatterFraction.format(feeds[position].amount)
            timestamp = dateTimeParser.toMillis(feeds[position].timestamp)
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding: FeedItemBinding = DataBindingUtil
            .inflate(
                parent.context.layoutInflater,
                R.layout.feed_item,
                parent, false
            )
        return FeedViewHolder(binding)
    }

    override fun getItemCount() = feeds.size

    inner class FeedViewHolder(internal val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}