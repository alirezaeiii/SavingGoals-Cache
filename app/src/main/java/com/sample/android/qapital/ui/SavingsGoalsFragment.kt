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
import com.sample.android.qapital.databinding.FragmentSavingsGoalsBinding
import com.sample.android.qapital.ui.SavingsGoalsAdapter.*
import com.sample.android.qapital.util.NumberFormatter
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.setupActionBar
import com.sample.android.qapital.viewmodels.SavingsGoalsViewModel
import javax.inject.Inject

class SavingsGoalsFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment<FragmentSavingsGoalsBinding>() {

    @Inject
    lateinit var viewModelFactory: SavingsGoalsViewModel.Factory

    @Inject
    lateinit var numberFormatter: NumberFormatter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val viewModel = ViewModelProvider(this, viewModelFactory)[SavingsGoalsViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_savings_goals, container, false)
        val binding = FragmentSavingsGoalsBinding.bind(root)
        applyDataBinding(binding, viewModel)

        val viewModelAdapter = SavingsGoalsAdapter(currencyFormatter, numberFormatter,
            OnClickListener { savingGoals ->
                    val destination = SavingsGoalsFragmentDirections.actionMainFragmentToDetailFragment(savingGoals)
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