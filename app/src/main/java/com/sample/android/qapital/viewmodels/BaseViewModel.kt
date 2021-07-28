package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

open class BaseViewModel<T>(
    private val schedulerProvider: BaseSchedulerProvider,
    private val requestObservable: Observable<T>
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _liveData = MutableLiveData<Resource<T>>()
    val liveData: LiveData<Resource<T>>
        get() = _liveData

    protected fun sendRequest() {
        sendRequest(requestObservable)
    }

    protected fun sendRequest(requestObservable: Observable<T>) {
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