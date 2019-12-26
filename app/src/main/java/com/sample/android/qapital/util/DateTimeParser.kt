package com.sample.android.qapital.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeParser {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    init {
        formatter.timeZone = TimeZone.getTimeZone("GMT")
    }

    fun toMillis(dateTime: String) = formatter.parse(dateTime).time
}