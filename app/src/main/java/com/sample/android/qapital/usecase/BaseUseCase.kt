package com.sample.android.qapital.usecase

import com.sample.android.qapital.util.EspressoIdlingResource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable

open class BaseUseCase(private val schedulerProvider: BaseSchedulerProvider) {

    protected fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
        .doOnSubscribe { EspressoIdlingResource.increment() }
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doFinally {
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
}