package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsRepository
import com.sample.android.qapital.usecase.SavingsGoalsUseCase
import com.sample.android.qapital.util.Resource
import timber.log.Timber
import javax.inject.Inject

class SavingsGoalsViewModel(
    private val dataSource: GoalsRepository,
    private val useCase: SavingsGoalsUseCase
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
        compositeDisposable.add(useCase.getSavingsGoals()
            .subscribe({ goals ->
                _liveData.postValue(Resource.Success(goals))
            }
            ) {
                _liveData.postValue(Resource.Failure(it.localizedMessage))
                Timber.e(it)
            })
    }

    class SavingsGoalsViewModelFactory @Inject constructor(
        private val dataSource: GoalsRepository,
        private val useCase: SavingsGoalsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SavingsGoalsViewModel(
                dataSource = dataSource,
                useCase = useCase
            ) as T
        }
    }
}