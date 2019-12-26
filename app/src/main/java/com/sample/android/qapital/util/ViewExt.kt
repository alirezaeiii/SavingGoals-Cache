package com.sample.android.qapital.util

import android.os.Build
import android.text.Html
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun View.visibleGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@Suppress("DEPRECATION")
fun String.fromHtml() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
} else {
    Html.fromHtml(this)
}