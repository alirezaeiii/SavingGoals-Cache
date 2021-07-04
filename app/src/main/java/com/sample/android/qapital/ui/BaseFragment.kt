package com.sample.android.qapital.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sample.android.qapital.util.formatter.CurrencyFormatter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment<T: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : DaggerFragment() {

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.apply {
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}