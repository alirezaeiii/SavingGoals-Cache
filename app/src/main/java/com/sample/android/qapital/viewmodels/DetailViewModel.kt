package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.util.CurrencyFormatterFraction
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel(
    api: QapitalService,
    schedulerProvider: BaseSchedulerProvider,
    private val currencyFormatter: CurrencyFormatterFraction,
    goal: SavingsGoal
) : BaseViewModel(schedulerProvider) {

    private val _liveData = MutableLiveData<Resource<DetailWrapper>>().apply {
        postValue(Resource.Loading())
    }
    val liveData: LiveData<Resource<DetailWrapper>>
        get() = _liveData

    init {
        val source1 = api.requestFeeds(goal.id).map { it.wrapper }
        val source2 = api.requestSavingRules().map { it.wrapper }

        composeObservable { Observable.zip(source1, source2,
                BiFunction<List<Feed>, List<SavingsRule>, DetailWrapper>
                { feeds, savingRules -> DetailWrapper(feeds,
                    savingRules.joinToString { it.type },
                    getWeekSumText(feeds))})
        }.subscribe({
            _liveData.postValue(Resource.Success(it))
        }) {
            _liveData.postValue(Resource.Failure(it.localizedMessage))
            Timber.e(it)
        }.also { compositeDisposable.add(it) }
    }

    class DetailWrapper(
        val feeds: List<Feed>,
        val savingRules: String,
        val weekSumText: String
    )

    class Factory @Inject constructor(
        private val api: QapitalService,
        private val schedulerProvider: BaseSchedulerProvider,
        private val currencyFormatter: CurrencyFormatterFraction,
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

    private fun getWeekSumText(feeds : List<Feed>) : String {
        var weekSum = 0f
        for (feed in feeds) {
            weekSum += getAmountIfInCurrentWeek(feed)
        }
        return currencyFormatter.format(weekSum)
    }

    private fun getAmountIfInCurrentWeek(feed: Feed): Float {
        val timestamp = ZonedDateTime.parse(feed.timestamp)
        val now = ZonedDateTime.now()
        return if (now.minus(1, ChronoUnit.WEEKS).isBefore(timestamp)) {
            feed.amount
        } else 0f
    }
}