package com.sample.android.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.sample.android.goals.BR
import com.sample.android.goals.util.formatter.CurrencyFormatter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM: ViewModel, T: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : DaggerFragment() {

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter

    protected abstract val viewModel: VM

    private var _binding: T? = null

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.apply {
            setVariable(BR.vm, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}