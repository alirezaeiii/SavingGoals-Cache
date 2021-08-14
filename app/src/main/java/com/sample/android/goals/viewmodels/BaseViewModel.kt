package com.sample.android.goals.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.android.goals.util.Resource
import com.sample.android.goals.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel<T>(
    private val schedulerProvider: BaseSchedulerProvider,
    private val requestObservable: Observable<T>
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _liveData = MutableLiveData<Resource<T>>()
    val liveData: LiveData<Resource<T>>
        get() = _liveData

    fun sendRequest() {
        _liveData.value = Resource.Loading
        requestObservable.subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui()).subscribe({
                _liveData.postValue(Resource.Success(it))
            }) {
                _liveData.postValue(Resource.Failure(it.localizedMessage))
                Timber.e(it)
            }.also { compositeDisposable.add(it) }
    }
    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all disposables;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}