package com.sample.android.qapital.viewmodels

import androidx.lifecycle.ViewModel
import com.sample.android.qapital.util.EspressoIdlingResource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(private val schedulerProvider: BaseSchedulerProvider) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    protected fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
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