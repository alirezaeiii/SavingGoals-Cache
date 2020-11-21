package com.sample.android.qapital.ui.detail

import com.sample.android.qapital.util.CurrencyFormatter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter
}