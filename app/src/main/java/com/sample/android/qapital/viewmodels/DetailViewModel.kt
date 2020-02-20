package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.usecase.DetailUseCase
import com.sample.android.qapital.util.CurrencyFormatterFraction
import com.sample.android.qapital.util.Resource
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DetailViewModel(
    useCase: DetailUseCase,
    goal: SavingsGoal
) : BaseViewModel() {

    private val currencyFormatter = CurrencyFormatterFraction(Locale.getDefault())

    private val _feeds = MutableLiveData<Resource<List<Feed>>>()
    val feeds: LiveData<Resource<List<Feed>>>
        get() = _feeds

    private val _weekSumText = MutableLiveData<String>()
    val weekSumText: LiveData<String>
        get() = _weekSumText

    private val _savingsRules = MutableLiveData<Resource<String>>()
    val savingsRules: LiveData<Resource<String>>
        get() = _savingsRules

    init {
        compositeDisposable.addAll(useCase.getFeeds(goal.id)
            .doOnSubscribe { _feeds.postValue(Resource.Loading()) }
            .subscribe({ feeds ->
                _feeds.postValue(Resource.Success(feeds))
                var weekSum = 0f
                for (feed in feeds) {
                    weekSum += getAmountIfInCurrentWeek(feed)
                }
                _weekSumText.postValue(currencyFormatter.format(weekSum))
            }
            ) {
                _feeds.postValue(Resource.Failure(it.localizedMessage))
                Timber.e(it)
            }
            , useCase.getSavingsRules()
                .doOnSubscribe { _savingsRules.postValue(Resource.Loading()) }
                .subscribe({ rules ->
                    var rule = ""
                    for (i in rules.indices) {
                        rule += rules[i].type
                        if (i != rules.size - 1) {
                            rule += ", "
                        }
                    }
                    _savingsRules.postValue(Resource.Success(rule))
                }
                ) {
                    _savingsRules.postValue(Resource.Failure(it.localizedMessage))
                    Timber.e(it)
                })
    }

    class Factory @Inject constructor(
        private val useCase: DetailUseCase,
        val goal: SavingsGoal
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(
                    useCase = useCase,
                    goal = goal
                ) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

    private fun getAmountIfInCurrentWeek(feed: Feed): Float {
        val timestamp = ZonedDateTime.parse(feed.timestamp)
        val now = ZonedDateTime.now()
        return if (now.minus(1, ChronoUnit.WEEKS).isBefore(timestamp)) {
            feed.amount
        } else 0f
    }
}