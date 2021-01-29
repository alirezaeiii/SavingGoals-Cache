package com.sample.android.qapital.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.android.qapital.util.EspressoIdlingResource
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseViewModel<T>(private val schedulerProvider: BaseSchedulerProvider) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected val goalLiveData = MutableLiveData<Resource<T>>()
    val liveData: LiveData<Resource<T>>
        get() = goalLiveData

    protected abstract val requestObservable: Observable<T>

    protected fun sendRequest() {
        composeObservable { requestObservable }.subscribe({
            goalLiveData.postValue(Resource.Success(it))
        }) {
            goalLiveData.postValue(Resource.Failure(it.localizedMessage))
            Timber.e(it)
        }.also { compositeDisposable.add(it) }
    }

    private fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
        .doOnSubscribe { EspressoIdlingResource.increment() }
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doFinally {
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
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