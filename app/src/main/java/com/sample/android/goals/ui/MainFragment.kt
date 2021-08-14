package com.sample.android.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.android.goals.R
import com.sample.android.goals.databinding.FragmentMainBinding
import com.sample.android.goals.ui.MainAdapter.OnClickListener
import com.sample.android.goals.util.Resource
import com.sample.android.goals.util.formatter.NumberFormatter
import com.sample.android.goals.util.setupActionBar
import com.sample.android.goals.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment @Inject
constructor() // Required empty public constructor
    : BaseFragment<MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    @Inject
    lateinit var numberFormatter: NumberFormatter

    override val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val viewModelAdapter = MainAdapter(currencyFormatter, numberFormatter,
            OnClickListener { savingGoals ->
                val destination =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(savingGoals)
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