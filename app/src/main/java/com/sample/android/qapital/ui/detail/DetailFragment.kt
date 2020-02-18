package com.sample.android.qapital.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.sample.android.qapital.BR
import com.sample.android.qapital.R
import com.sample.android.qapital.databinding.FragmentDetailBinding
import com.sample.android.qapital.util.CurrencyFormatter
import com.sample.android.qapital.util.setupActionBar
import com.sample.android.qapital.viewmodels.DetailViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailFragment @Inject
constructor() // Required empty public constructor
    : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    @Inject
    lateinit var currencyFormatter: CurrencyFormatter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewModel = ViewModelProviders.of(this, viewModelFactory)[DetailViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        val binding = FragmentDetailBinding.bind(root).apply {
            setVariable(BR.vm, viewModel)
            goal = viewModelFactory.goal
            formatter = currencyFormatter
            lifecycleOwner = viewLifecycleOwner
        }

        with(activity as AppCompatActivity) {
            setupActionBar(binding.toolbar) {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }

        return root
    }
}