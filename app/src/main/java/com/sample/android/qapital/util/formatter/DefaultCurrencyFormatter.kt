package com.sample.android.qapital.util.formatter

import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DefaultCurrencyFormatter @Inject constructor(locale: Locale) {

    protected val formatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    fun format(number: Any): String = formatter.format(number)
}