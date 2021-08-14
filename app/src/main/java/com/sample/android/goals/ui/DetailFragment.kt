package com.sample.android.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.sample.android.goals.R
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.databinding.FragmentDetailBinding
import com.sample.android.goals.util.Resource
import com.sample.android.goals.util.formatter.DefaultCurrencyFormatter
import com.sample.android.goals.util.setupActionBar
import com.sample.android.goals.viewmodels.DetailViewModel
import javax.inject.Inject

class DetailFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment<DetailViewModel, FragmentDetailBinding>(R.layout.fragment_detail) {

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    @Inject
    lateinit var goal: SavingsGoal

    @Inject
    lateinit var defaultCurrencyFormatter: DefaultCurrencyFormatter

    override val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding.apply {
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
                addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
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