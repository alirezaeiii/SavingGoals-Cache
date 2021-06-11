package com.sample.android.qapital.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.android.qapital.R
import com.sample.android.qapital.databinding.FragmentMainBinding
import com.sample.android.qapital.ui.MainAdapter.*
import com.sample.android.qapital.util.NumberFormatter
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.setupActionBar
import com.sample.android.qapital.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment<FragmentMainBinding>() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    @Inject
    lateinit var numberFormatter: NumberFormatter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val binding = FragmentMainBinding.bind(root)
        applyDataBinding(binding, viewModel)

        val viewModelAdapter = MainAdapter(currencyFormatter, numberFormatter,
            OnClickListener { savingGoals ->
                    val destination = MainFragmentDirections.actionMainFragmentToDetailFragment(savingGoals)
                    with(findNavController()) {
                        currentDestination?.getAction(destination.actionId)
                            ?.let { navigate(destination) }
                    }
                })

        with(binding) {
            swipeRefresh.apply {
                setColorSchemeColors(
                    ContextCompat.getColor(context, R.color.colorAccent),
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    ContextCompat.getColor(context, R.color.colorPrimaryDark)
                )
            }

            recyclerView.apply {
                setHasFixedSize(true)
                adapter = viewModelAdapter
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, {
            if (it is Resource.Success) {
                viewModelAdapter.submitList(it.data?.wrapper)
            }
        })

        (activity as AppCompatActivity).setupActionBar(binding.toolbar) {
            setTitle(R.string.savings_goals)
        }

        return binding.root
    }
}