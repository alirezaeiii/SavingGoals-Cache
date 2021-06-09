package com.sample.android.qapital.ui

import androidx.databinding.ViewDataBinding
import com.sample.android.qapital.util.CurrencyFormatter
import dagger.android.support.DaggerFragment
import androidx.lifecycle.ViewModel
import javax.inject.Inject

open class BaseFragment<T: ViewDataBinding> : DaggerFragment() {

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter

    protected fun applyDataBinding(binding: T, viewModel: ViewModel, variableId: Int) {
        binding.apply {
            setVariable(variableId, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }
    }
}