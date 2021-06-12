package com.sample.android.qapital.util

import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberFormatter @Inject constructor(locale: Locale) : DefaultCurrencyFormatter(locale) {

    init {
        val decimalFormatSymbols = (formatter as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        formatter.decimalFormatSymbols = decimalFormatSymbols
        formatter.maximumFractionDigits = 0
    }
}