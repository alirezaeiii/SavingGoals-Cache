package com.sample.android.qapital.util.formatter

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFormatter @Inject constructor(locale: Locale) : DefaultCurrencyFormatter(locale) {

    init {
        formatter.maximumFractionDigits = 0
    }
}