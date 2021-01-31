package com.sample.android.qapital.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.sample.android.qapital.data.asWeekSumText
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.CurrencyFormatterDefault
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.viewmodels.DetailViewModel.DetailWrapper
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DetailViewModel(api: QapitalService, schedulerProvider: BaseSchedulerProvider,
    currencyFormatter: CurrencyFormatterDefault, goal: SavingsGoal
) : BaseViewModel<DetailWrapper>(schedulerProvider,
    Observable.zip(api.requestFeeds(goal.id).map { it.wrapper },
        api.requestSavingRules().map { it.wrapper },
        BiFunction<List<Feed>, List<SavingsRule>, DetailWrapper>
        { feeds, savingRules -> DetailWrapper(feeds,
            savingRules.joinToString { it.type },
            feeds.asWeekSumText(currencyFormatter)
        ) })) {

    init {
        sendRequest()
    }

    class DetailWrapper(
        val feeds: List<Feed>,
        val savingRules: String,
        val weekSumText: String
    )

    class Factory @Inject constructor(
        private val api: QapitalService,
        private val schedulerProvider: BaseSchedulerProvider,
        private val currencyFormatter: CurrencyFormatterDefault,
        val goal: SavingsGoal
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