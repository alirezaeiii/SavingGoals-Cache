package com.sample.android.qapital.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.source.BaseRepository
import com.sample.android.qapital.network.SavingsGoalWrapper
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import javax.inject.Inject

class MainViewModel(
    private val repository: BaseRepository<SavingsGoalWrapper>,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel<SavingsGoalWrapper>(schedulerProvider, repository.result) {

    init {
        loadSavingsGoals(false)
    }

    fun loadSavingsGoals(isRefreshing: Boolean) {
        if (isRefreshing) {
            repository.refresh()
        }
        super.sendRequest()
    }

    class Factory @Inject constructor(
        private val repository: BaseRepository<SavingsGoalWrapper>,
        private val schedulerProvider: BaseSchedulerProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository, schedulerProvider) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}