package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsRepository
import com.sample.android.qapital.util.EspressoIdlingResource
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class SavingsGoalsViewModel(
    private val schedulerProvider: BaseSchedulerProvider,
    private val dataSource: GoalsRepository
) : BaseViewModel() {

    private val _liveData = MutableLiveData<Resource<List<SavingsGoal>>>()
    val liveData: LiveData<Resource<List<SavingsGoal>>>
        get() = _liveData

    init {
        load()
    }

    fun load() {
        _liveData.postValue(Resource.Loading())
        showSavingsGoals()
    }

    fun refresh() {
        _liveData.postValue(Resource.Reloading())
        dataSource.refreshGoals()
        showSavingsGoals()
    }

    private fun showSavingsGoals() {
        EspressoIdlingResource.increment() // App is busy until further notice
        compositeDisposable.add(dataSource.getSavingsGoals()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally {
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
            }
            .subscribe({ goals ->
                _liveData.postValue(Resource.Success(goals))
            }
            ) {
                _liveData.postValue(Resource.Failure(it.localizedMessage))
                Timber.e(it)
            })
    }

    class SavingsGoalsViewModelFactory @Inject constructor(
        private val schedulerProvider: BaseSchedulerProvider,
        private val dataSource: GoalsRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SavingsGoalsViewModel(
                schedulerProvider = schedulerProvider,
                dataSource = dataSource
            ) as T
        }
    }
}