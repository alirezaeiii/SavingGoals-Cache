package com.sample.android.goals.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.goals.data.Feed
import com.sample.android.goals.databinding.FeedItemBinding
import com.sample.android.goals.ui.FeedAdapter.FeedViewHolder
import com.sample.android.goals.util.DateTimeParser
import com.sample.android.goals.util.formatter.DefaultCurrencyFormatter
import com.sample.android.goals.util.fromHtml
import com.sample.android.goals.util.layoutInflater

class FeedAdapter(
    feeds: List<Feed>,
    private val currencyFormatter: DefaultCurrencyFormatter
) : RecyclerView.Adapter<FeedViewHolder>() {

    private var feeds: List<Feed> = feeds
        set(feeds) {
            field = feeds
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feeds[position]
        with(holder.binding) {
            message.text = feed.message.fromHtml()
            amount.text = currencyFormatter.format(feed.amount)
            timestamp = DateTimeParser.toMillis(feed.timestamp)
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = FeedItemBinding.inflate(
            parent.context.layoutInflater,
            parent, false
        )
        return FeedViewHolder(binding)
    }

    override fun getItemCount() = feeds.size

    class FeedViewHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root)
}