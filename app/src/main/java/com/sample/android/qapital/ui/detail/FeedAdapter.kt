package com.sample.android.qapital.ui.detail

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.qapital.R
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.ui.detail.FeedAdapter.FeedViewHolder
import com.sample.android.qapital.databinding.FeedItemBinding
import com.sample.android.qapital.util.*

class FeedAdapter(
    feeds: List<Feed>,
    private val currencyFormatter: CurrencyFormatterDefault
) : RecyclerView.Adapter<FeedViewHolder>() {

    private val dateTimeParser = DateTimeParser

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
            timestamp = dateTimeParser.toMillis(feed.timestamp)
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

    class FeedViewHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root)
}