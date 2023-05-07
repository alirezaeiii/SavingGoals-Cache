package com.sample.android.goals.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.data.asWeekSumText
import com.sample.android.goals.data.source.DetailsRepository
import com.sample.android.goals.util.formatter.DefaultCurrencyFormatter
import com.sample.android.goals.util.schedulers.BaseSchedulerProvider
import com.sample.android.goals.viewmodels.DetailViewModel.DetailWrapper
import io.reactivex.Observable
import javax.inject.Inject

class DetailViewModel(
    repository: DetailsRepository, schedulerProvider: BaseSchedulerProvider,
    currencyFormatter: DefaultCurrencyFormatter, goal: SavingsGoal
) : BaseViewModel<DetailWrapper>(schedulerProvider,
    Observable.zip(
        repository.getFeeds(goal.id), repository.getSavingRules()
    ) { feeds, savingRules ->
        DetailWrapper(
            feeds,
            savingRules.joinToString { it.type },
            feeds.asWeekSumText(currencyFormatter)
        )
    }) {

    init {
        sendRequest()
    }

    class DetailWrapper(
        val feeds: List<Feed>,
        val savingRules: String,
        val weekSumText: String
    )

    class Factory @Inject constructor(
        private val repository: DetailsRepository,
        private val schedulerProvider: BaseSchedulerProvider,
        private val currencyFormatter: DefaultCurrencyFormatter,
        private val goal: SavingsGoal
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(repository, schedulerProvider, currencyFormatter, goal) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}