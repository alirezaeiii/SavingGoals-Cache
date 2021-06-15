package com.sample.android.qapital.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.sample.android.qapital.R
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.databinding.FragmentDetailBinding
import com.sample.android.qapital.util.formatter.DefaultCurrencyFormatter
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.setupActionBar
import com.sample.android.qapital.viewmodels.DetailViewModel
import javax.inject.Inject

class DetailFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment<FragmentDetailBinding>() {

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    @Inject
    lateinit var goal: SavingsGoal

    @Inject
    lateinit var defaultCurrencyFormatter: DefaultCurrencyFormatter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        val binding = FragmentDetailBinding.bind(root).apply {
            applyDataBinding(this, viewModel)
            goal = this@DetailFragment.goal
            formatter = currencyFormatter
        }

        with(activity as AppCompatActivity) {
            setupActionBar(binding.toolbar) {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }

        with(binding) {
            toolbar.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
            }
            recyclerView.apply {
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(recyclerView.context,
                        DividerItemDecoration.VERTICAL))
                viewModel.liveData.observe(viewLifecycleOwner, {
                    if (it is Resource.Success) {
                        adapter = it.data?.let { data ->
                            FeedAdapter(data.feeds, defaultCurrencyFormatter)
                        }
                    }
                })
            }
        }
        return binding.root
    }
}