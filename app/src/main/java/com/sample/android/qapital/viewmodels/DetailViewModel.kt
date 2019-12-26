package com.sample.android.qapital.viewmodels

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.remote.QapitalRemoteDataSource
import com.sample.android.qapital.util.CurrencyFormatterFraction
import com.sample.android.qapital.util.EspressoIdlingResource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DetailViewModel(
    private val schedulerProvider: BaseSchedulerProvider,
    private val dataSource: QapitalRemoteDataSource,
    private val goal : SavingsGoal
) : BaseViewModel() {

    private val currencyFormatter = CurrencyFormatterFraction(Locale.getDefault())

    val feeds: ObservableList<Feed> = ObservableArrayList()
    val weekSumText = ObservableField<String>()
    val savingsRules = ObservableField<String>()
    private val _isFeedsLoading = MutableLiveData<Boolean>()
    val isFeedsLoading: LiveData<Boolean>
        get() = _isFeedsLoading
    val isRulesLoading = ObservableBoolean(false)
    val isFeedsLoadingError = ObservableBoolean(false)
    val isRulesLoadingError = ObservableBoolean(false)

    init {
        showFeeds()
        showSavingsRules()
    }

    private fun showFeeds() {
        EspressoIdlingResource.increment() // App is busy until further notice
        _isFeedsLoading.postValue(true)
        compositeDisposable.add(dataSource.getFeeds(goal.id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally {
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                _isFeedsLoading.postValue(false)
            }
            .subscribe({ feeds ->
                isFeedsLoadingError.set(false)
                with(this.feeds) {
                    clear()
                    addAll(feeds)
                }
                var weekSum = 0f
                for (feed in feeds) {
                    weekSum += getAmountIfInCurrentWeek(feed)
                }
                weekSumText.set(currencyFormatter.format(weekSum))
            }
            ) {
                isFeedsLoadingError.set(true)
                Timber.e(it)
            })
    }

    private fun showSavingsRules() {
        EspressoIdlingResource.increment() // App is busy until further notice
        isRulesLoading.set(true)
        compositeDisposable.addAll(dataSource.getSavingsRules()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally {
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                isRulesLoading.set(false)
            }
            .subscribe({ rules ->
                isRulesLoadingError.set(false)
                var rule = ""
                for (i in rules.indices) {
                    rule += rules[i].type
                    if (i != rules.size - 1) {
                        rule += ", "
                    }
                }
                savingsRules.set(rule)
            }
            ) {
                isRulesLoadingError.set(true)
                Timber.e(it)
            })
    }

    class DetailViewModelFactory @Inject constructor(
        private val schedulerProvider: BaseSchedulerProvider,
        private val dataSource: QapitalRemoteDataSource,
        private val goal : SavingsGoal
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(
                schedulerProvider = schedulerProvider,
                dataSource = dataSource,
                goal = goal
            ) as T
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