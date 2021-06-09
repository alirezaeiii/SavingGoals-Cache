package com.sample.android.qapital.util

import android.graphics.Bitmap
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sample.android.qapital.R
import com.sample.android.qapital.ui.SavingsGoalsAdapter
import com.sample.android.qapital.viewmodels.DetailViewModel

object BindingUtils {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(cardView: CardView, url: String) {
        Glide.with(cardView.context)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().centerCrop())
                .into(object : BitmapImageViewTarget(cardView.findViewById(R.id.image_poster)) {
                    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        super.onResourceReady(bitmap, transition)
                        Palette.from(bitmap).generate { palette ->
                            val color = palette!!.getVibrantColor(
                                    ContextCompat.getColor(
                                            cardView.context,
                                            R.color.black_translucent_60
                                    )
                            )
                            cardView.findViewById<View>(R.id.title_background).setBackgroundColor(color)
                        }
                    }
                })
    }

    @JvmStatic
    @BindingAdapter("refreshing")
    fun <T> setSwipeRefreshLayout(view: SwipeRefreshLayout, resource: Resource<T>?) {
        view.isRefreshing = resource is Resource.Loading
    }

    @JvmStatic
    @BindingAdapter("showLoading")
    fun <T> showLoading(view: ProgressBar, resource: Resource<T>?) {
        view.visibility = if (resource is Resource.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("showData")
    fun <T> showData(recyclerView: RecyclerView, resource: Resource<T>?) {
        if (resource is Resource.Failure) {
            (recyclerView.adapter as SavingsGoalsAdapter).submitList(null)
        }
    }

    @JvmStatic
    @BindingAdapter("showData")
    fun <T> showData(view: View, resource: Resource<T>?) {
        view.visibility = if (resource is Resource.Success) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun <T> showError(view: View, resource: Resource<T>?) {
        view.visibility = if (resource is Resource.Failure) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("descriptive_date")
    fun setDescriptiveDate(view: TextView, timestamp: Long) {
        val now = System.currentTimeMillis()
        val relativeDate = DateUtils.getRelativeTimeSpanString(
                timestamp,
                now,
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
        )
        view.text = relativeDate
    }

    @JvmStatic
    @BindingAdapter("android:max")
    fun setProgressBarMax(view: ProgressBar, value: Float) {
        view.max = value.toInt()
    }

    @JvmStatic
    @BindingAdapter("android:progress")
    fun setProgressBarProgress(view: ProgressBar, value: Float) {
        view.progress = value.toInt()
    }

    @JvmStatic
    @BindingAdapter("sum")
    fun setSumText(textView: TextView, resource: Resource<DetailViewModel.DetailWrapper>?) {
        if (resource is Resource.Success) {
            textView.text = resource.data?.weekSumText
        }
    }

    @JvmStatic
    @BindingAdapter("rules")
    fun setRulesText(textView: TextView, resource: Resource<DetailViewModel.DetailWrapper>?) {
        if (resource is Resource.Success) {
            textView.text = resource.data?.savingRules
        }
    }
}