package com.sample.android.qapital.util

import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumberFormatter @Inject constructor() {

    private val numberFormat = NumberFormat.getCurrencyInstance()

    init {
        val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        numberFormat.decimalFormatSymbols = decimalFormatSymbols
        numberFormat.maximumFractionDigits = 0
    }

    fun format(number: Any): String = numberFormat.format(number)
}