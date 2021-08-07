package com.sample.android.qapital.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.asWeekSumText
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.formatter.DefaultCurrencyFormatter
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.viewmodels.DetailViewModel.DetailWrapper
import io.reactivex.Observable
import javax.inject.Inject

class DetailViewModel(api: QapitalService, schedulerProvider: BaseSchedulerProvider,
                      currencyFormatter: DefaultCurrencyFormatter, goal: SavingsGoal
) : BaseViewModel<DetailWrapper>(schedulerProvider) {

    init {
        sendRequest(Observable.zip(api.requestFeeds(goal.id).map { it.wrapper },
            api.requestSavingRules().map { it.wrapper },
            { feeds, savingRules -> DetailWrapper(feeds,
                savingRules.joinToString { it.type },
                feeds.asWeekSumText(currencyFormatter)
            ) }))
    }

    class DetailWrapper(
        val feeds: List<Feed>,
        val savingRules: String,
        val weekSumText: String
    )

    class Factory @Inject constructor(
        private val api: QapitalService,
        private val schedulerProvider: BaseSchedulerProvider,
        private val currencyFormatter: DefaultCurrencyFormatter,
        private val goal: SavingsGoal
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(api, schedulerProvider, currencyFormatter, goal) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}