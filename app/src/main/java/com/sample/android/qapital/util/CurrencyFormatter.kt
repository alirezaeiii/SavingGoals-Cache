package com.sample.android.qapital.util

import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFormatter @Inject constructor(locale: Locale) {

    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    init {
        formatter.maximumFractionDigits = 0
    }

    fun format(number: Any): String = formatter.format(number)

}