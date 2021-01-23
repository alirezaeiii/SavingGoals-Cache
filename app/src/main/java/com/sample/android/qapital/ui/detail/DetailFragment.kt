package com.sample.android.qapital.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.BR
import com.sample.android.qapital.R
import com.sample.android.qapital.databinding.FragmentDetailBinding
import com.sample.android.qapital.ui.BaseFragment
import com.sample.android.qapital.util.setupActionBar
import com.sample.android.qapital.viewmodels.DetailViewModel
import javax.inject.Inject

class DetailFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

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

        return binding.root
    }
}