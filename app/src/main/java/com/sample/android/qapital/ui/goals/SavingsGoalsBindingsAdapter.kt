package com.sample.android.qapital.ui.goals

import android.graphics.Bitmap
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sample.android.qapital.R
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.util.Resource

object SavingsGoalsBindingsAdapter {

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
    fun setSwipeRefreshLayout(view: SwipeRefreshLayout, resource: Resource<List<SavingsGoal>>?) {
        view.isRefreshing = resource is Resource.Loading
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, resource: Resource<List<SavingsGoal>>?) {
        view.visibility = if (resource is Resource.Failure) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("showData")
    fun showData(view: View, resource: Resource<List<SavingsGoal>>?) {
        view.visibility = if (resource is Resource.Success) View.VISIBLE else View.GONE
    }
}